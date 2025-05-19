package prod;

import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class CardServiceImpl extends CardServiceGrpc.CardServiceImplBase {

    @Override
    public void createUser(UserProductService.CreateUserReq request, StreamObserver<UserProductService.CreateUserResp> responseObserver) {
        long res = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        System.out.printf("createUser: email: '%s' username: '%s'%n", request.getEmail(), request.getUsername());
        UserProductService.CreateUserResp response = UserProductService.CreateUserResp.newBuilder()
                .setId(res)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserEmail(UserProductService.ChangeUserEmailReq request, StreamObserver<UserProductService.VoidResp> responseObserver) {
        System.out.printf("changeUserEmail: id: '%s', email: '%s' %n", request.getId(), request.getEmail());
        responseObserver.onNext(UserProductService.VoidResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserName(UserProductService.ChangeUserNameReq request, StreamObserver<UserProductService.VoidResp> responseObserver) {
        System.out.printf("changeUserName: id: '%s', username: '%s' %n", request.getId(), request.getUsername());
        responseObserver.onNext(UserProductService.VoidResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void createProduct(UserProductService.CreateProductReq request, StreamObserver<UserProductService.CreateProductResp> responseObserver) {
        long res = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        System.out.printf("createProduct: name: '%s'%n", request.getName());
        UserProductService.CreateProductResp response = UserProductService.CreateProductResp.newBuilder()
                .setId(res)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addProductToCart(UserProductService.AddProductToCartReq request, StreamObserver<UserProductService.VoidResp> responseObserver) {
        System.out.printf("addProductToCart: ProductId: '%s', UserId: '%s'%n", request.getProductId(), request.getUserId());
        responseObserver.onNext(UserProductService.VoidResp.newBuilder().build());
        responseObserver.onCompleted();
    }
}
