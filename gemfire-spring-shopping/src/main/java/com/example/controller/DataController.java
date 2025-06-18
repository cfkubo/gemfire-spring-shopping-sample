package com.example.controller;

import com.example.model.DataItem;
import com.example.service.DataService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final DataService dataService;

    // @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    // @GetMapping("/data/{id}")
    // public DataItem getDataById(@PathVariable String id) {
    //     return dataService.getDataById(id);
    // }
    @GetMapping("/data/{id}")
    public DataItem getDataById(@PathVariable("id") String id) {
        return dataService.getDataById(id);
    }
}