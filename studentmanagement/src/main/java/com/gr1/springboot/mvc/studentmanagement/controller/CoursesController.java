package com.gr1.springboot.mvc.studentmanagement.controller;

import com.gr1.springboot.mvc.studentmanagement.model.Courses;
import com.gr1.springboot.mvc.studentmanagement.service.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class CoursesController {

    private final CoursesService coursesService;

    @GetMapping("/courses")
    public List<Courses> findAll() {
        return coursesService.findAll();
    }

    @GetMapping("/courses/{coursesId}")
    public Courses getCourses(@PathVariable Long coursesId) {

        Courses theCourses = coursesService.findById(coursesId);

        if (theCourses == null) {
            throw new RuntimeException("Did not find course id: " + coursesId);
        }

        return theCourses;
    }

    @PostMapping("/courses")
    public Courses addCourses(@RequestBody Courses theCourses) {

        theCourses.setCourseId(null);

        Courses dbCourses = coursesService.save(theCourses);

        return dbCourses;
    }

    @PutMapping("/courses")
    public Courses updateCourses(@RequestBody Courses theCourses) {

        Courses dbCourses = coursesService.save(theCourses);

        return dbCourses;
    }

    @DeleteMapping("/courses/{coursesId}")
    public String deleteCourses(@PathVariable Long coursesId) {

        Courses tempCourses = coursesService.findById(coursesId);

        if (tempCourses == null) {
            throw new RuntimeException("Did not find courses id: " + coursesId);
        }

        coursesService.deleteById(coursesId);

        return "Deleted courses id: " + coursesId;
    }
}
