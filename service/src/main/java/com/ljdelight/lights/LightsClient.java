//
// Copyright (c) Lucas Burson
// Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
//

package com.ljdelight.lights;

import com.ljdelight.lights.generated.Center;
import com.ljdelight.lights.generated.Comment;
import com.ljdelight.lights.generated.Lights;
import com.ljdelight.lights.generated.Location;
import com.ljdelight.lights.generated.TaggedLocationWithMeta;
import com.ljdelight.lights.generated.Thing;
import com.ljdelight.lights.generated.ThingType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.List;

public class LightsClient {
    private static final Logger logger = LogManager.getLogger(LightsClient.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            logger.error("{} given invalid arguments", LightsClient.class.getName());
            System.err.println("LightsClient <host> <port>");
            System.exit(1);
        }
        logger.trace("In LightsClient main()");

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        logger.debug("Input arguments are host={} port={}", host, port);
        try (TTransport transport = new TFramedTransport(new TSocket(host, port))) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Lights.Client client = new Lights.Client(protocol);

            client.upvoteThing(0, new Thing(28374, ThingType.LOCATION));
            List<TaggedLocationWithMeta> locations =
                    client.getLocationsWithMetaNear(new Center(new Location(37.6889, -97.3361)));
            for (TaggedLocationWithMeta location : locations) {
                StringBuilder s = new StringBuilder();
                for (Comment c : location.meta.comments) {
                    s.append('[').append(c.id).append(":").append(c.comment).append(']');
                }
                System.out.println(String.format("%d,%d,%f,%f,%s", location.tag.uid, location.tag.votes,
                        location.tag.location.getLat(), location.tag.location.getLng(),
                        s.toString()));
            }
            System.out.println("Found " + locations.size());
            transport.close();
        } catch (TTransportException x) {
            logger.error("Caught TTransportException", x);
            System.exit(1);
        } catch (TException x) {
            logger.error("Caught TException", x);
            x.printStackTrace();
        }
    }
}
