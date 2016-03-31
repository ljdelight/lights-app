//
// Copyright (c) Lucas Burson
// Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
//

package com.ljdelight.lights;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
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
                    try {
                        p.load(LightsHandler.class.getResourceAsStream("/database.properties"));
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

    private ResultSet executeQuery(String q) throws SQLException {
        Connection c = connect();

        Statement stmt = c.createStatement();
        ResultSet set = stmt.executeQuery(q);
        return set;
    }

    @Override
    public List<Location> getAllLocations() throws TException {
        try {
            List<Location> locations = new ArrayList<>();

            String sql = "SELECT ST_X(the_geom) AS lng, ST_Y(the_geom) AS lat FROM lights";
            ResultSet set = executeQuery(sql);
            while (set.next()) {
                String lat = set.getString("lat");
                String lng = set.getString("lng");
                locations.add(new Location(Double.parseDouble(lat), Double.parseDouble(lng)));
            }
            set.close();
            return locations;
        } catch (SQLException e) {
            logger.error("Failed to connect to db");
            throw new TException(e);
        }
    }

    @Override
    public List<Location> getLocationsNear(Center center) throws TException {

        try {
            String sql = String.format(
                    "SELECT " + "ST_X(the_geom) AS lng, ST_Y(the_geom) AS lat " + "FROM lights " + "WHERE "
                            + "ST_DWithin(" + "   the_geom, " + "   ST_SetSRID(" + "      ST_MakePoint(%s, %s), "
                            + "      4326), " + "   %d, " + "   false) " + "LIMIT 5000;",
                    Double.toString(center.location.getLng()), Double.toString(center.location.getLat()),
                    center.radiusInMeters);
            logger.error(sql);
            ResultSet set = executeQuery(sql);

            List<Location> locations = new ArrayList<>();
            while (set.next()) {
                locations.add(new Location(set.getDouble("lat"), set.getDouble("lng")));
            }
            set.close();
            return locations;
        } catch (SQLException e) {
            logger.error("Failed to connect to db");
            throw new TException(e);
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
