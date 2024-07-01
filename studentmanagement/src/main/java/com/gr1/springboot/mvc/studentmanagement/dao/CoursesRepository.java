package com.gr1.springboot.mvc.studentmanagement.dao;

import com.gr1.springboot.mvc.studentmanagement.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Courses, Long> {

}
