package task6.module.api.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/test")
public interface TestApi {

    @GetMapping
    ResponseEntity<String> test();
}
