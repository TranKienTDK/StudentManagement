package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.dao.CourseRegistrationRepository;
import com.gr1.springboot.mvc.studentmanagement.dao.CoursesRepository;
import com.gr1.springboot.mvc.studentmanagement.dao.StudentRepository;
import com.gr1.springboot.mvc.studentmanagement.dto.StudentRegistrationSummary;
import com.gr1.springboot.mvc.studentmanagement.model.CourseRegistration;
import com.gr1.springboot.mvc.studentmanagement.model.Courses;
import com.gr1.springboot.mvc.studentmanagement.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final StudentRepository studentRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public void registerCourse(Long studentId, Long courseId) {
        Optional<CourseRegistration> existingRegistration = courseRegistrationRepository.findByStudentStudentIdAndCourseCourseId(studentId, courseId);

        if (existingRegistration.isPresent()) {
            CourseRegistration registration = existingRegistration.get();
            if (!registration.getStatus().equals("Registered")) {
                registration.setStatus("Registered");
                registration.setRegistrationDate(new Date());
                courseRegistrationRepository.save(registration);
            } else {
                throw new IllegalArgumentException("Course already registered.");
            }
        } else {
            Optional<Student> student = studentRepository.findById(studentId);
            Optional<Courses> course = coursesRepository.findById(courseId);

            if (student.isPresent() && course.isPresent()) {
                CourseRegistration registration = new CourseRegistration();
                registration.setStudent(student.get());
                registration.setCourse(course.get());
                registration.setStatus("Registered");
                registration.setRegistrationDate(new Date());
                courseRegistrationRepository.save(registration);
            } else {
                throw new IllegalArgumentException("Student or Course not found.");
            }
        }
    }

    @Override
    public void unregisterCourse(Long studentId, Long courseId) {
        Optional<CourseRegistration> existingRegistration = courseRegistrationRepository.findByStudentStudentIdAndCourseCourseId(studentId, courseId);

        if (existingRegistration.isPresent()) {
            CourseRegistration registration = existingRegistration.get();
            if (registration.getStatus().equals("Registered")) {
                registration.setStatus("Unregistered");
                courseRegistrationRepository.save(registration);
            } else {
                throw new IllegalArgumentException("Course not registered.");
            }
        } else {
            throw new IllegalArgumentException("Course registration not found.");
        }
    }

    @Override
    public List<CourseRegistration> getRegistratedCourses(Long studentId) {
        return courseRegistrationRepository.findByStudentStudentId(studentId);
    }

    @Override
    // Lấy danh sách sinh viên đã đăng ký theo courseId
    public List<StudentRegistrationSummary> getAllStudentRegistrationSummaries(Long courseId) {
        return courseRegistrationRepository.findAllRegisteredStudentSummariesByCourseId(courseId);
    }
}
