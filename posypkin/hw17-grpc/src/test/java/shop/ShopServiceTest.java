package shop;

import io.grpc.*;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.jupiter.api.*;
import shop.ProductService.*;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private Server server;
    private ManagedChannel channel;
    private ShopServiceGrpc.ShopServiceBlockingStub blockingStub;

    @BeforeEach
    void setUp() throws Exception {
        // Уникальное имя для in-process сервера
        String serverName = InProcessServerBuilder.generateName();

        // Создаём и запускаем gRPC-сервер в памяти
        server = InProcessServerBuilder
                .forName(serverName)
                .addService(new ShopServiceImpl())
                .directExecutor() // тесты быстрее
                .build()
                .start();

        // Канал к in-process серверу
        channel = InProcessChannelBuilder
                .forName(serverName)
                .directExecutor()
                .build();

        // Блокирующий клиент (stub)
        blockingStub = ShopServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    void tearDown() {
        channel.shutdownNow();
        server.shutdownNow();
    }

    @Test
    void testCreateUser() {
        ProductService.CreateUserRequest request = CreateUserRequest.newBuilder()
                .setEmail("test@example.com")
                .setUsername("testuser")
                .build();

        CreateUserResponse response = blockingStub.createUser(request);

        assertNotNull(response);
        assertTrue(response.getId() > 0);
    }

    @Test
    void testChangeUserEmail() {
        ChangeUserEmailRequest request = ChangeUserEmailRequest.newBuilder()
                .setId(1L)
                .setEmail("new@example.com")
                .build();

        Empty response = blockingStub.changeUserEmail(request);
        assertNotNull(response);
    }

    @Test
    void testCreateProduct() {
        CreateProductRequest request = CreateProductRequest.newBuilder()
                .setName("TestProduct")
                .build();

        CreateProductResponse response = blockingStub.createProduct(request);

        assertNotNull(response);
        assertTrue(response.getId() > 0);
    }

    @Test
    void testAddProductToCart() {
        AddProductToCartRequest request = AddProductToCartRequest.newBuilder()
                .setUserId(1L)
                .setProductId(2L)
                .build();

        Empty response = blockingStub.addProductToCart(request);
        assertNotNull(response);
    }
}
