package task6.module.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import task6.module.api.controllers.api.TestApi;
import task6.module.service.Task6Service;

import java.util.UUID;


@RestController
public class TestController implements TestApi {

    private final Task6Service task6Service;
    Logger logger = LoggerFactory.getLogger(TestController.class);

    public TestController() {
        this.task6Service = new Task6Service();
    }

    @Override
    public ResponseEntity<String> test() {
        UUID runId = UUID.randomUUID();
        String result = task6Service.serviceData();
        logger.info(String.format("Controller: result: '%s', result: '%s'", runId, result));
        return new ResponseEntity<>(result, HttpStatus.resolve(200));
    }
}
