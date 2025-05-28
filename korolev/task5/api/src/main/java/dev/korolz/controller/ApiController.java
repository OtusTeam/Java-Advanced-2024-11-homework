package dev.korolz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.korolz.DataProvider;
import dev.korolz.DataService;

import java.util.Map;

@RestController
public class ApiController {

    private final DataService dataService;

    public ApiController() {
        DataProvider provider = new DataProvider();
        this.dataService = new DataService(provider);
    }

    @GetMapping("/save")
    public String saveData() {
        long id = dataService.saveData();
        return "Data saved with ID: " + id;
    }

    @GetMapping("/all")
    public Map<Long, String> getAllData() {
        return dataService.getAll();
    }
}
