package org.example.task18;

import org.example.task18.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CircuitBreakerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private UserService userService;

    final List<ResponseEntity<String>> responses = new ArrayList<>();

//    @Test
//    void circuitBreakerCountOpenTest() {
//        var numberSuccessfulFirst = 4;
//        var numberNotSuccessful = 10;
//
//        Mockito.when(userService.clone())
//                .thenReturn("", returnMockSuccess(numberSuccessfulFirst - 1))
//                .thenThrow(returnMockNotSuccess(numberNotSuccessful));
//
//        IntStream.rangeClosed(1, numberSuccessfulFirst + numberNotSuccessful)
//                .forEach(it -> responses.add(testRestTemplate.getForEntity("/circuit-breaker", String.class)));
//
//        verify(userService, times(11)).clone();
//
//        assertEquals(numberSuccessfulFirst + numberNotSuccessful, responses.size());
//        assertEquals(numberSuccessfulFirst,
//                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
//        assertEquals(7,
//                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR).count());
//        assertEquals(3,
//                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE).count());
//
//    }
}
