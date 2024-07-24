package com.gr1.springboot.mvc.studentmanagement.dao;

import com.gr1.springboot.mvc.studentmanagement.dto.StudentRegistrationSummary;
import com.gr1.springboot.mvc.studentmanagement.model.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findByStudentStudentId(Long studentId);
    Optional<CourseRegistration> findByStudentStudentIdAndCourseCourseId(Long studentId, Long courseId);

    // Truy vấn tùy chỉnh để lấy các bản ghi đã đăng ký theo courseId
    @Query("SELECT new com.gr1.springboot.mvc.studentmanagement.dto.StudentRegistrationSummary(" +
            "cr.student.studentId, cr.student.fullName, cr.course.courseId, cr.course.courseName, cr.registrationDate, cr.status) " +
            "FROM CourseRegistration cr " +
            "WHERE (cr.status = 'registered' OR cr.status = 'unregistered') AND cr.course.courseId = :courseId")
    List<StudentRegistrationSummary> findAllRegisteredStudentSummariesByCourseId(@Param("courseId") Long courseId);

    // Phương thức tùy chỉnh để lấy các khóa học của một sinh viên dựa trên studentId
    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.student.studentId = :studentId")
    List<CourseRegistration> findCoursesByStudentId(@Param("studentId") Long studentId);
}
