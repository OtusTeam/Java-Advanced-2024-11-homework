package ru.otus.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@GrpcService
@Slf4j
public class UserProductServiceImpl extends UserProductServiceGrpc.UserProductServiceImplBase {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final Map<Long, List<Long>> userCarts = new ConcurrentHashMap<>();
    private final AtomicLong userIdGenerator = new AtomicLong();
    private final AtomicLong productIdGenerator = new AtomicLong();

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserID> responseObserver) {
        long id = userIdGenerator.incrementAndGet();
        User user = User.newBuilder()
                .setId(id)
                .setEmail(request.getEmail())
                .setUsername(request.getUsername())
                .build();
        users.put(id, user);
        var response = UserID.newBuilder().setValue(id).build();
        responseObserver.onNext(response);
        log.info("Created user {}", response);
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserEmail(ChangeEmailRequest request, StreamObserver<Empty> responseObserver) {
        users.computeIfPresent(request.getId(), (id, user) ->
                user.toBuilder().setEmail(request.getEmail()).build());
        var response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        log.info("Changed user email {}", request.getEmail());
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserName(ChangeUsernameRequest request, StreamObserver<Empty> responseObserver) {
        users.computeIfPresent(request.getId(), (id, user) ->
                user.toBuilder().setUsername(request.getUsername()).build());
        var response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        log.info("Changed user name {}", request.getUsername());
        responseObserver.onCompleted();
    }

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<ProductID> responseObserver) {
        long id = productIdGenerator.incrementAndGet();
        Product product = Product.newBuilder()
                .setId(id)
                .setName(request.getName())
                .build();
        products.put(id, product);
        responseObserver.onNext(ProductID.newBuilder().setValue(id).build());
        log.info("Created product {}", product);
        responseObserver.onCompleted();
    }

    @Override
    public void addProductToCart(AddProductToCartRequest request, StreamObserver<Empty> responseObserver) {
        userCarts.computeIfAbsent(request.getUserId(), k -> new ArrayList<>())
                .add(request.getProductId());
        responseObserver.onNext(Empty.newBuilder().build());
        log.info("Added product to cart {} userID {}", request.getProductId(), request.getUserId());
        responseObserver.onCompleted();
    }
}