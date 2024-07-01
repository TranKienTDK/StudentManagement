package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.model.Courses;

import java.util.List;

public interface CoursesService {

    List<Courses> findAll();

    Courses findById(Long theId);

    Courses save(Courses theCourses);

    void deleteById(Long theId);

    Long count();
}
