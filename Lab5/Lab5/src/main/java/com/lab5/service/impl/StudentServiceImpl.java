package com.lab5.service.impl;

import com.lab5.entity.Student;
import com.lab5.repository.StudentRepository;
import com.lab5.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(String id) {
        studentRepository.deleteById(id);
    }
}
