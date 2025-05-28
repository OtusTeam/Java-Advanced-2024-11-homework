package shop;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ShopServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(9090)
                .addService(new ShopServiceImpl())
                .build();

        server.start();
        System.out.println("Server started on port 9090");

        server.awaitTermination();
    }
}

