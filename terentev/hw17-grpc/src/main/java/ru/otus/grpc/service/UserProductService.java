package ru.otus.grpc.service;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import ru.otus.grpc.*;

import java.util.*;

public class UserProductService extends UserProductServiceGrpc.UserProductServiceImplBase {

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, Product> productMap = new HashMap<>();
    private final Map<String, List<String>> userCartMap = new HashMap<>();

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        String userId = UUID.randomUUID().toString();
        User user = User.newBuilder()
                .setId(userId)
                .setEmail(request.getEmail())
                .setUsername(request.getUsername())
                .build();

        userMap.put(userId, user);
        userCartMap.put(userId, new ArrayList<>());

        CreateUserResponse response = CreateUserResponse.newBuilder()
                .setId(userId)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserEmail(ChangeUserEmailRequest request, StreamObserver<Empty> responseObserver) {
        User user = userMap.get(request.getId());
        if (user != null) {
            User updatedUser = user.toBuilder()
                    .setEmail(request.getEmail())
                    .build();
            userMap.put(request.getId(), updatedUser);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription("User not found")));
        }
    }

    @Override
    public void changeUserName(ChangeUserNameRequest request, StreamObserver<Empty> responseObserver) {
        User user = userMap.get(request.getId());
        if (user != null) {
            User updatedUser = user.toBuilder()
                    .setUsername(request.getUsername())
                    .build();
            userMap.put(request.getId(), updatedUser);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription("User not found")));
        }
    }

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<CreateProductResponse> responseObserver) {
        String productId = UUID.randomUUID().toString();
        Product product = Product.newBuilder()
                .setId(productId)
                .setName(request.getName())
                .build();

        productMap.put(productId, product);

        CreateProductResponse response = CreateProductResponse.newBuilder()
                .setId(productId)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addProductToCart(AddProductToCartRequest request, StreamObserver<Empty> responseObserver) {
        String userId = request.getUserId();
        String productId = request.getProductId();

        if (!userMap.containsKey(userId)) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription("User not found")));
            return;
        }

        if (!productMap.containsKey(productId)) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription("Product not found")));
            return;
        }

        List<String> cart = userCartMap.getOrDefault(userId, new ArrayList<>());
        cart.add(productId);
        userCartMap.put(userId, cart);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
