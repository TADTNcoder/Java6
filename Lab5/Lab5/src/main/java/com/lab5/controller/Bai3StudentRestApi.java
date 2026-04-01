package com.lab5.controller;



import com.lab5.entity.Student;
import com.lab5.util.Database;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
public class Bai3StudentRestApi {

    @GetMapping("/students.json")
    public Map<String, Student> findAll() {
        return Database.map;
    }

    @GetMapping("/students/{key}.json")
    public Student findByKey(@PathVariable("key") String key) {
        return Database.map.get(key);
    }

    @PostMapping("/students.json")
    public Map<String, String> create(@RequestBody Student student) {
        String key = Database.getKey();
        Database.map.put(key, student);
        return Map.of("name", key);
    }

    @PutMapping("/students/{key}.json")
    public Student update(@PathVariable("key") String key, @RequestBody Student student) {
        Database.map.put(key, student);
        return Database.map.get(key);
    }

    @DeleteMapping("/students/{key}.json")
    public void delete(@PathVariable("key") String key) {
        Database.map.remove(key);
    }
}