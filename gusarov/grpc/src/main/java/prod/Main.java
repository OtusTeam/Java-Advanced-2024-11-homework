package prod;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Main {
    public static void main(String[] args) throws Exception {

        Server server = ServerBuilder.forPort(8097)
                .addService(new CardServiceImpl())
                .build();

        server.start();
        System.out.println("Started!");
        server.awaitTermination();
    }
}