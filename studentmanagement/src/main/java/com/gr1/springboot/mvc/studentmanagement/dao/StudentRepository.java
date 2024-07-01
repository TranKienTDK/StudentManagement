package com.gr1.springboot.mvc.studentmanagement.dao;

import com.gr1.springboot.mvc.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // All methods CRUD will create automatically using Spring Data JPA
    Optional<Student> findByAccount_UserName(String userName);

}
