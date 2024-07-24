package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.dto.StudentRegistrationSummary;
import com.gr1.springboot.mvc.studentmanagement.model.CourseRegistration;

import java.util.List;

public interface CourseRegistrationService {

    void registerCourse(Long studentId, Long courseId);

    void unregisterCourse(Long studentId, Long courseId);

    List<CourseRegistration> getRegistratedCourses(Long studentId);

    List<StudentRegistrationSummary> getAllStudentRegistrationSummaries(Long courseId);

    List<CourseRegistration> getRegisteredCourses(Long studentId);
}
