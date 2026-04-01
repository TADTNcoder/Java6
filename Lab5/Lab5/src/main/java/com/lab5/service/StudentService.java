package com.lab5.service;

import com.lab5.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(String id);
    Student create(Student student);
    Student update(Student student);
    void deleteById(String id);
}
