package com.gr1.springboot.mvc.studentmanagement.controller;

import com.gr1.springboot.mvc.studentmanagement.model.Student;
import com.gr1.springboot.mvc.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    // expose "/students" and return a list of students
    @GetMapping("/students")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    // expose "/students/{studentId}" and return a student
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable Long studentId) {

        Student theStudent = studentService.findById(studentId);

        if (theStudent == null) {
            throw new RuntimeException("Student id not found - " + studentId);
        }

        return theStudent;
    }

    // expose "/students" and add a student
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student theStudent) {

        theStudent.setStudentId(null);

        Student dbStudent = studentService.save(theStudent);

        return dbStudent;
    }

    // expose "/students/" and update a student
    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student theStudent) {

        Student dbStudent = studentService.save(theStudent);

        return dbStudent;
    }

    // expose "/students/{studentid}" and delete a student by id
    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {

        Student tempStudent = studentService.findById(studentId);

        if (tempStudent == null) {
            throw new RuntimeException("Student id not found - " + studentId);
        }

        studentService.deleteById(studentId);

        return "Deleted student id - " + studentId;
    }

}
