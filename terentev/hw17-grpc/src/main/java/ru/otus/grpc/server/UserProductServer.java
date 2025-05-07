package ru.otus.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.otus.grpc.service.UserProductService;

import java.io.IOException;
import java.util.logging.Logger;

public class UserProductServer {

    private static final Logger logger = Logger.getLogger(UserProductServer.class.getName());

    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder
                .forPort(port)
                .addService(new UserProductService())
                .build()
                .start();
        logger.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("*** shutting down gRPC server since JVM is shutting down");
            UserProductServer.this.stop();
            logger.info("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final UserProductServer server = new UserProductServer();
        server.start();
        server.blockUntilShutdown();
    }
}
