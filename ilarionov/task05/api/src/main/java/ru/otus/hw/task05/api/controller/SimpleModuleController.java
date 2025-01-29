package ru.otus.hw.task05.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.task05.api.dto.ModuleDto;
import ru.otus.hw.task05.service.DataService;

@RestController
public class SimpleModuleController {

    private final DataService dataService;

    public SimpleModuleController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/get/{id}")
    public ModuleDto getDataById(@PathVariable("id") long id) {
        var text = dataService.get(id);
        return new ModuleDto(id, text);
    }

    @GetMapping("/put/{text}")
    public ModuleDto putData(@PathVariable("text") String text) {
        var id = dataService.put(text);
        return new ModuleDto(id, text);
    }
}
