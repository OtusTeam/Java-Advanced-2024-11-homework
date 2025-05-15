import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.service.CircuitBreakerService;
import ru.otus.service.RateLimiterMinService;
import ru.otus.service.RateLimiterSecService;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResiliencePatternsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RateLimiterSecService rateLimiterSecService;

    @MockBean
    private RateLimiterMinService rateLimiterMinService;

    @MockBean
    private CircuitBreakerService circuitBreakerService;

    @Test
    void testRateLimiterPerSecond() {
        int allowed = 2;
        int total = 6;
        Long userId = 1L;

        when(rateLimiterSecService.getUserAge(userId)).thenReturn(42);

        var responses = new CopyOnWriteArrayList<ResponseEntity<Integer>>();

        IntStream.range(0, total).parallel().forEach(i -> {
            var response = restTemplate.getForEntity("/users/" + userId + "/age/sec", Integer.class);
            responses.add(response);
        });

        long success = responses.stream().filter(r -> r.getStatusCode() == HttpStatus.OK).count();
        long limited = responses.stream().filter(r -> r.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS).count();

        assertEquals(total, responses.size());
        assertEquals(allowed, success);
        assertEquals(total - allowed, limited);
        verify(rateLimiterSecService, times(allowed)).getUserAge(userId);
    }

    @Test
    void testRateLimiterPerMinute() throws InterruptedException {
        int allowed = 4;
        int perBatch = 2;
        int repeats = 3;
        Long userId = 2L;

        when(rateLimiterMinService.getUserAge(userId)).thenReturn(42);

        var responses = new CopyOnWriteArrayList<ResponseEntity<Integer>>();

        for (int i = 0; i < repeats; i++) {
            for (int j = 0; j < perBatch; j++) {
                responses.add(restTemplate.getForEntity("/users/" + userId + "/age/min", Integer.class));
            }
            Thread.sleep(1000);
        }

        long success = responses.stream().filter(r -> r.getStatusCode() == HttpStatus.OK).count();
        long limited = responses.stream().filter(r -> r.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS).count();

        assertEquals(perBatch * repeats, responses.size());
        assertEquals(allowed, success);
        assertEquals(perBatch * repeats - allowed, limited);
        verify(rateLimiterMinService, times(allowed)).getUserAge(userId);
    }

    @Test
    void testCircuitBreaker() {
        Long userId = 3L;

        when(circuitBreakerService.getUserAge(userId))
                .thenThrow(new RuntimeException("fail 1"))
                .thenThrow(new RuntimeException("fail 2"))
                .thenReturn(42)
                .thenThrow(new RuntimeException("fail 3"))
                .thenReturn(42);

        var responses = IntStream.range(0, 5)
                .mapToObj(i -> restTemplate.getForEntity("/users/" + userId + "/age/cb", Integer.class))
                .toList();

        long serviceUnavailable = responses.stream()
                .filter(r -> r.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE)
                .count();

        assertTrue(serviceUnavailable >= 1, "CircuitBreaker должен активироваться хотя бы раз");
        verify(circuitBreakerService, atMost(5)).getUserAge(userId);
    }
}