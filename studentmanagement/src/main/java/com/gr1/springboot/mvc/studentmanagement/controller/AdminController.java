package com.gr1.springboot.mvc.studentmanagement.controller;

import com.gr1.springboot.mvc.studentmanagement.model.Courses;
import com.gr1.springboot.mvc.studentmanagement.model.Student;
import com.gr1.springboot.mvc.studentmanagement.service.CoursesService;
import com.gr1.springboot.mvc.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StudentService studentService;

    private final CoursesService coursesService;

    @GetMapping("/students")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    // Expose "/students" and add a student (only for admin)
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student theStudent) {
        Student dbStudent = studentService.save(theStudent);
        return dbStudent;
    }

    @PutMapping("/students/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student theStudent) {
        Student dbStudent = studentService.findById(studentId);

        if (dbStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update student details
        dbStudent.setFullName(theStudent.getFullName());
        dbStudent.setDateOfBirth(theStudent.getDateOfBirth());
        dbStudent.setEmail(theStudent.getEmail());
        dbStudent.setPhoneNumber(theStudent.getPhoneNumber());
        dbStudent.setAddress(theStudent.getAddress());

        studentService.save(dbStudent);

        return ResponseEntity.ok(dbStudent);
    }


    // Expose "/students/{studentId}" and delete a student by id (only for admin)
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        Student tempStudent = studentService.findById(studentId);

        if (tempStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student id not found - " + studentId);
        }

        studentService.deleteById(studentId);
        return ResponseEntity.ok("Deleted student id - " + studentId);
    }

    // UPDATE HOC PHAN
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Courses> updateCourse(@PathVariable Long courseId, @RequestBody Courses theCourses) {
        Courses dbCourses = coursesService.findById(courseId);

        if (dbCourses == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update details
        assert dbCourses != null;
        dbCourses.setCourseName(theCourses.getCourseName());
        dbCourses.setCourseDescription(theCourses.getCourseDescription());
        dbCourses.setCredits(theCourses.getCredits());

        coursesService.save(dbCourses);
        return ResponseEntity.ok(dbCourses);
    }

    // GET SO LUONG SINH VIEN
    @GetMapping("/students/count")
    public ResponseEntity<Long> countStudents() {
        return ResponseEntity.ok(studentService.count());
    }

    // GET SO LUONG HOC PHAN
    @GetMapping("/courses/count")
    public ResponseEntity<Long> countCourses() {
        return ResponseEntity.ok(coursesService.count());
    }
}
