package ru.otus.hw.task05.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.task05.service.DataService;
import ru.otus.hw.task05.service.dto.ServiceDto;

@RestController
public class SimpleModuleController {

    private final DataService dataService;

    public SimpleModuleController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/get/{id}")
    public ServiceDto getDataById(@PathVariable("id") long id) {
        return dataService.get(id);
    }

    @GetMapping("/put/{text}")
    public ServiceDto putData(@PathVariable("text") String text) {
        return dataService.put(text);
    }
}
