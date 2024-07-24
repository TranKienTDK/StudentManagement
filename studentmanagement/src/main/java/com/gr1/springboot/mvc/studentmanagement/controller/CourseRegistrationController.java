package com.gr1.springboot.mvc.studentmanagement.controller;

import com.gr1.springboot.mvc.studentmanagement.dto.CourseRegistrationRequest;
import com.gr1.springboot.mvc.studentmanagement.dto.StudentRegistrationSummary;
import com.gr1.springboot.mvc.studentmanagement.model.CourseRegistration;
import com.gr1.springboot.mvc.studentmanagement.service.CourseRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerCourse(@RequestBody CourseRegistrationRequest request) {
        try {
            courseRegistrationService.registerCourse(request.getStudentId(), request.getCourseId());
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Registered course " + request.getCourseId() + " for student " + request.getStudentId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/unregister")
    public ResponseEntity<Map<String, String>> unregisterCourse(@RequestBody CourseRegistrationRequest request) {
        try {
            courseRegistrationService.unregisterCourse(request.getStudentId(), request.getCourseId());
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Unregistered course " + request.getCourseId() + " for student " + request.getStudentId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/courses/{studentId}")
    public List<CourseRegistration> getRegistedCourses(@PathVariable Long studentId) {
        return courseRegistrationService.getRegistratedCourses(studentId);
    }

    // API để lấy danh sách các bản ghi đã đăng ký theo courseId
    @GetMapping("/registered")
    public List<StudentRegistrationSummary> getRegisteredStudentSummariesByCourseId(@RequestParam Long courseId) {
        return courseRegistrationService.getAllStudentRegistrationSummaries(courseId);
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<List<CourseRegistration>> getCoursesByStudentId(@PathVariable Long studentId) {
        List<CourseRegistration> registrations = courseRegistrationService.getRegisteredCourses(studentId);
        return ResponseEntity.ok(registrations);
    }
}
