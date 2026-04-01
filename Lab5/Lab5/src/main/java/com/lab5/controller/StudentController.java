package com.lab5.controller;

import com.lab5.entity.Student;
import com.lab5.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    public Student findById(@PathVariable("id") String id) {
        return studentService.findById(id);
    }

    @PostMapping("/students")
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @PutMapping("/students/{id}")
    public Student update(@PathVariable("id") String id, @RequestBody Student student) {
        student.setId(id);
        return studentService.update(student);
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable("id") String id) {
        studentService.deleteById(id);
    }
}