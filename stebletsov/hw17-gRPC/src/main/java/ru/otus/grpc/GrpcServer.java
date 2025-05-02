package ru.otus.grpc;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcServer implements CommandLineRunner {

    private final UserProductServiceImpl service;

    @Autowired
    public GrpcServer(UserProductServiceImpl service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        Server server = ServerBuilder.forPort(9090)
                .addService((BindableService) service)
                .build()
                .start();
        System.out.println("gRPC Server started on port 9090");
        server.awaitTermination();
    }
}
