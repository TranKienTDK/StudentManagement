package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    Student findById(Long theId);

    Student save(Student theStudent);

    void deleteById(Long theId);

    Optional<Student> findByUserName(String userName);

    Long count();
}
