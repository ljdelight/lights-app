//
// Copyright (c) Lucas Burson
// Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
//

package com.ljdelight.lights;

import com.ljdelight.lights.generated.Center;
import com.ljdelight.lights.generated.Comment;
import com.ljdelight.lights.generated.Lights;
import com.ljdelight.lights.generated.Location;
import com.ljdelight.lights.generated.Meta;
import com.ljdelight.lights.generated.TaggedLocation;
import com.ljdelight.lights.generated.TaggedLocationWithMeta;
import com.ljdelight.lights.generated.Thing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LightsHandler implements Lights.Iface {
    private static final Logger logger = LogManager.getLogger(LightsHandler.class);
    private static volatile Connection s_connection = null;

    private static Connection connect() throws SQLException {
        if (s_connection == null) {
            synchronized (LightsHandler.class) {
                if (s_connection == null) {
                    logger.info("Database connection is not cached; connecting");
                    Properties p = new Configuration();
                    try (InputStream stream = LightsHandler.class.getResourceAsStream(
                                 "/database.properties")) {
                        p.load(stream);
                    } catch (IOException e) {
                        throw new RuntimeException("Could not read database.properties", e);
                    }

                    String url = p.getProperty("db.url");
                    String port = p.getProperty("db.port");
                    String db = p.getProperty("db.database");
                    String user = p.getProperty("db.user");
                    String pw = p.getProperty("db.password");

                    String fullUrl = String.format("%s:%s/%s", url, port, db);
                    Properties props = new Properties();
                    props.setProperty("user", user);
                    props.setProperty("password", pw);
                    props.setProperty("ssl", "false");

                    s_connection = DriverManager.getConnection(fullUrl, props);
                }
            }
        }
        return s_connection;
    }

    @Override
    public List<Location> getAllLocations() throws TException {
        List<Location> locations = new ArrayList<>();
        try (Statement statement = LightsHandler.connect().createStatement()) {
            String query = "SELECT ST_X(the_geom) AS lng, ST_Y(the_geom) AS lat FROM locations";
            try (ResultSet set = statement.executeQuery(query)) {
                while (set.next()) {
                    String lat = set.getString("lat");
                    String lng = set.getString("lng");
                    locations.add(new Location(Double.parseDouble(lat), Double.parseDouble(lng)));
                }
            }
            return locations;
        } catch (SQLException e) {
            logger.error("Failed to connect to db");
            throw new TException(e);
        }
    }

    @Override
    public List<TaggedLocation> getLocationsNear(Center center) throws TException {
        PreparedStatement statement = null;
        try {
            statement = LightsHandler.connect().prepareStatement(
                    "SELECT id, ST_X(the_geom) AS lng, ST_Y(the_geom) AS lat, votes FROM locations WHERE "
                    + "ST_DWithin(the_geom, ST_SetSRID(ST_MakePoint(?,?), "
                    + "4326), ?, false) "
                    + "LIMIT 5000;");
            statement.setDouble(1, center.location.getLng());
            statement.setDouble(2, center.location.getLat());
            statement.setInt(3, center.radiusInMeters);

            logger.debug(statement);
            ResultSet set = statement.executeQuery();

            List<TaggedLocation> locations = new ArrayList<>();
            while (set.next()) {
                locations.add(new TaggedLocation(set.getLong("id"),
                        new Location(set.getDouble("lat"), set.getDouble("lng")),
                        set.getInt("votes")));
            }
            set.close();
            return locations;
        } catch (SQLException e) {
            logger.error("Failed to connect to db");
            throw new TException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error(
                            "Failed to close the sql statement. Logging the error and continuing.");
                    // Log and keep going in this case
                }
            }
        }
    }

    private static class Configuration extends Properties {
        private static final long serialVersionUID = 6455585423012660835L;

        public String getProperty(final String key) {
            final String property = super.getProperty(key);
            if (property == null) {
                throw new InvalidParameterException(
                        MessageFormat.format("Missing value for key {0}!", key));
            }
            return property;
        }
    }

    @Override
    public List<TaggedLocationWithMeta> getLocationsWithMetaNear(Center center) throws TException {
        List<TaggedLocation> locs = getLocationsNear(center);
        PreparedStatement statement = null;
        try {
            List<TaggedLocationWithMeta> ret = new ArrayList<>();

            for (TaggedLocation loc : locs) {
                statement = LightsHandler.connect().prepareStatement(
                        "SELECT comment_id AS id, comment, votes FROM comments JOIN locations_comments AS loc ON id=loc.location_id WHERE id=?;");
                statement.setLong(1, loc.uid);

                logger.debug(statement);
                ResultSet set = statement.executeQuery();

                Meta meta = new Meta(new ArrayList<Comment>());

                while (set.next()) {
                    Comment c = new Comment(
                            set.getInt("votes"), set.getString("comment"), set.getLong("id"));
                    meta.comments.add(c);
                }
                ret.add(new TaggedLocationWithMeta().setTag(loc).setMeta(meta));
                set.close();
            }
            return ret;
        } catch (SQLException e) {
            logger.error("Failed to connect to db");
            throw new TException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Failed to close sql statement. Logging error and continuing.");
                    // Log the error and keep going in this case
                }
            }
        }
    }

    @Override
    public long upvoteThing(long token, Thing thing) throws TException {
        logger.debug("In upvoteThing");
        String table = "";
        switch (thing.type) {
            case COMMENT:
                table = "comments";
                break;
            case LOCATION:
                table = "locations";
                break;
            default:
                throw new TException("thing type is not valid");
        }

        PreparedStatement statement = null;
        try {
            // TODO need to disallow duplicate votes from a single token.
            statement = LightsHandler.connect().prepareStatement(
                    "BEGIN; UPDATE ? SET votes=votes+1 WHERE id=?; COMMIT;");
            statement.setString(1, table);
            statement.setLong(2, thing.id);

            statement.execute();
        } catch (SQLException e) {
            logger.error("Failed to connect to db");
            throw new TException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    logger.debug("Finished upvote for the table '" + table + "'");
                } catch (SQLException e) {
                    logger.error("Failed to close sql statement. Logging error and continuing.");
                    // Log the error and keep going in this case
                }
            }
        }
        return 0;
    }
}
