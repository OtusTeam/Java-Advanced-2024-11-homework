package shop;

import io.grpc.stub.StreamObserver;

public class ShopServiceImpl extends ShopServiceGrpc.ShopServiceImplBase {

    @Override
    public void createUser(ProductService.CreateUserRequest request, StreamObserver<ProductService.CreateUserResponse> responseObserver) {
        // Генерируем фиктивный id
        long userId = System.currentTimeMillis(); // Можно заменить на UUID или базу данных

        ProductService.CreateUserResponse response = ProductService.CreateUserResponse.newBuilder()
                .setId(userId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserEmail(ProductService.ChangeUserEmailRequest request, StreamObserver<ProductService.Empty> responseObserver) {
        System.out.println("Changing email for user " + request.getId() + " to " + request.getEmail());
        responseObserver.onNext(ProductService.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserName(ProductService.ChangeUserNameRequest request, StreamObserver<ProductService.Empty> responseObserver) {
        System.out.println("Changing username for user " + request.getId() + " to " + request.getUsername());
        responseObserver.onNext(ProductService.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void createProduct(ProductService.CreateProductRequest request, StreamObserver<ProductService.CreateProductResponse> responseObserver) {
        long productId = System.nanoTime(); // Фейковый id
        ProductService.CreateProductResponse response = ProductService.CreateProductResponse.newBuilder()
                .setId(productId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addProductToCart(ProductService.AddProductToCartRequest request, StreamObserver<ProductService.Empty> responseObserver) {
        System.out.println("Adding product " + request.getProductId() + " to cart of user " + request.getUserId());
        responseObserver.onNext(ProductService.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
