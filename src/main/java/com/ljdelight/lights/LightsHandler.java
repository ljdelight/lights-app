//
// Copyright (c) Lucas Burson
// Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
//

package com.ljdelight.lights;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import com.ljdelight.lights.generated.Center;
import com.ljdelight.lights.generated.Lights;
import com.ljdelight.lights.generated.Location;

public class LightsHandler implements Lights.Iface {
    private static final Logger logger = LogManager.getLogger(LightsHandler.class);
    private static volatile Connection s_connection = null;

    private static Connection connect() throws SQLException {
        if (s_connection == null) {
            synchronized (LightsHandler.class) {
                if (s_connection == null) {
                    logger.info("Database connection is not cached; connecting");
                    Properties p = new Configuration();
                    try (InputStream stream = LightsHandler.class.getResourceAsStream("/database.properties")) {
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
            String query = "SELECT ST_X(the_geom) AS lng, ST_Y(the_geom) AS lat FROM lights";
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
    public List<Location> getLocationsNear(Center center) throws TException {
        PreparedStatement statement = null;
        try {
            statement = LightsHandler.connect()
                    .prepareStatement("SELECT ST_X(the_geom) AS lng, ST_Y(the_geom) AS lat FROM lights WHERE "
                            + "ST_DWithin(the_geom, ST_SetSRID(ST_MakePoint(?,?), " + "4326), ?, false) "
                            + "LIMIT 5000;");
            statement.setString(1, Double.toString(center.location.getLng()));
            statement.setString(2, Double.toString(center.location.getLat()));
            statement.setInt(3, center.radiusInMeters);

            logger.debug(statement);
            ResultSet set = statement.executeQuery();

            List<Location> locations = new ArrayList<>();
            while (set.next()) {
                locations.add(new Location(set.getDouble("lat"), set.getDouble("lng")));
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
                    // Do nothing in this case
                }
            }
        }
    }

    private static class Configuration extends Properties {
        private static final long serialVersionUID = 6455585423012660835L;

        public String getProperty(final String key) {
            final String property = super.getProperty(key);
            if (property == null) {
                throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", key));
            }
            return property;
        }
    }
}
