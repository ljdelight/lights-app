//
// Copyright (c) Lucas Burson
// Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
//

package com.ljdelight.lights;

import com.ljdelight.lights.generated.Lights;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

public class LightsServer {
    private static final Logger logger = LogManager.getLogger(LightsServer.class);

    private static LightsHandler handler;
    private static Lights.Processor<LightsHandler> processor;

    public static void main(String[] args) {
        if (args.length != 1) {
            logger.error("Missing argument to {}", LightsServer.class.getName());
            System.err.println("LightsServer <port>");
            System.exit(1);
        }
        try {
            logger.trace("In LightsServer main()");
            handler = new LightsHandler();
            processor = new Lights.Processor<LightsHandler>(handler);
            int port = Integer.parseInt(args[0]);

            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    simple(processor, port);
                }
            };

            new Thread(simple).start();

        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(Lights.Processor<LightsHandler> processor, int port) {
        try {
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
            TServer server = new TNonblockingServer(
                    new TNonblockingServer.Args(serverTransport).processor(processor));
            logger.info("Starting server on port {}", port);

            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
