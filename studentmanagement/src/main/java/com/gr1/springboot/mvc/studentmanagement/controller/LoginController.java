package com.gr1.springboot.mvc.studentmanagement.controller;

import com.gr1.springboot.mvc.studentmanagement.dto.LoginForm;
import com.gr1.springboot.mvc.studentmanagement.dto.StudentRegistrationSummary;
import com.gr1.springboot.mvc.studentmanagement.model.Account;
import com.gr1.springboot.mvc.studentmanagement.model.CourseRegistration;
import com.gr1.springboot.mvc.studentmanagement.model.Courses;
import com.gr1.springboot.mvc.studentmanagement.model.Student;
import com.gr1.springboot.mvc.studentmanagement.service.AccountService;
import com.gr1.springboot.mvc.studentmanagement.service.CourseRegistrationService;
import com.gr1.springboot.mvc.studentmanagement.service.CoursesService;
import com.gr1.springboot.mvc.studentmanagement.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    private final StudentService studentService;

    private final CoursesService coursesService;

    private final CourseRegistrationService courseRegistrationService;


    @GetMapping("/login-form")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login-form")
    public String processLogin(LoginForm loginForm, Model model) {
        String userName = loginForm.getUsername();
        String password = loginForm.getPassword();

        Optional<Account> accountOptional = accountService.authenticate(userName, password);

        try {
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                log.info("account:", account);
                if (account.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
                    return "redirect:/home-page";
                }
                else if (account.getRole().equalsIgnoreCase("ROLE_USER")){
//                    model.addAttribute("error", "Access denied. You are not an admin.");
                    return "redirect:/studentHome";
                }
                else {
                    model.addAttribute("error", "Invalid role");
                    return "login";
                }
            } else {
                log.error("error in login");
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } catch (Exception e) {
            log.error("Error in LoginController:" + e.getMessage());
            return "login";
        }
    }

    @GetMapping("/home-page")
    public String showHomePage(Model theModel) {

        // Kiểm tra vai trò của người dùng
        boolean isAdmin = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (!isAdmin) {
            // Nếu không phải là admin, chuyển hướng về trang đăng nhập
            return "redirect:/studentHome";
        }

        // Get the students from database
        List<Student> studentList = studentService.findAll();

        // Add to the spring model
        theModel.addAttribute("student", studentList);

        return "home";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        // Create model attribute to bind form data
        Student student = new Student();
        model.addAttribute("student", student);
        return "add-student-form"; // Tên của view (HTML template) để hiển thị form thêm sinh viên
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student,
                              @RequestParam("userName") String userName,
                              @RequestParam("password") String password,
                              @RequestParam("role") String role) {
        // Tạo một tài khoản mới
        Account account = new Account();
        account.setUserName(userName);
        account.setPassword(password);
        account.setRole(role);
        // Lưu tài khoản mới vào cơ sở dữ liệu
        accountService.save(account);
        // Gán tài khoản mới cho sinh viên
        student.setAccount(account);
        // Lưu sinh viên vào database
        studentService.save(student);
        return "redirect:/home-page"; // Chuyển hướng về trang chủ hoặc danh sách sinh viên
    }

    @GetMapping("/update-student/{studentId}")
    public String showFormForUpdate(@PathVariable Long studentId, Model model) {
        Student theStudent = studentService.findById(studentId);

        if (theStudent == null) {
            throw new RuntimeException("Student id not found - " + studentId);
        }

        model.addAttribute("student", theStudent);
        return "update-student";
    }

    // THEM HOC PHAN
    @GetMapping("/showFormForAddCourse")
    public String showFormForAddCourse(Model model) {
        // Create model attribute to bind data
        Courses courses = new Courses();
        model.addAttribute("courses", courses);
        return "add-course-form";
    }

    // LUU HOC PHAN
    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute("course") Courses courses) {
        coursesService.save(courses);
        return "redirect:/home-page";
    }

    // CAP NHAT HOC PHAN
    @GetMapping("/update-course/{courseId}")
    public String showFormForUpdateCourse(@PathVariable Long courseId, Model model) {
        Courses theCourse = coursesService.findById(courseId);

        if (theCourse == null) {
            throw new RuntimeException("Course id not found - " + courseId);
        }

        model.addAttribute("courses", theCourse);
        return "update-course";
    }

    // DANG XUAT TRANG CHU ADMIN
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Hủy bỏ phiên làm việc hiện tại để đăng xuất
        }
        return "redirect:/login-form"; // Chuyển hướng về trang đăng nhập sau khi đăng xuất
    }

    // TRANG CHU SINH VIEN
    @GetMapping("/studentHome")
    public String showStudentHome(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Student> studentOptional = studentService.findByUserName(userName);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            model.addAttribute("student", student);

            // Lấy ảnh từ session
            byte[] uploadedImage = (byte[]) session.getAttribute("uploadedImage");
            model.addAttribute("uploadedImage", uploadedImage);

            // Add thêm thông tin hoạt động gần nhất
            model.addAttribute("recentCourse", "Course 101");
            model.addAttribute("recentResult", "A");

            return "student-home";
        } else {
            return "redirect:/login-form";
        }
    }

    // Trang đăng ký học phần
    @GetMapping("/register-courses")
    public String showRegisterCourses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Student> studentOptional = studentService.findByUserName(userName);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            model.addAttribute("student", student);

            // Lấy danh sách các khóa học đã đăng ký của sinh viên
            List<CourseRegistration> registeredCourses = courseRegistrationService.getRegistratedCourses(student.getStudentId());
            model.addAttribute("registeredCourses", registeredCourses);

            // Lấy danh sách các khóa học chưa được đăng ký
            List<Courses> allCourses = coursesService.findAll();
            List<Courses> availableCourses = allCourses.stream()
                    .filter(course -> registeredCourses.stream()
                            .noneMatch(reg -> reg.getCourse().getCourseId().equals(course.getCourseId())))
                    .collect(Collectors.toList());
            model.addAttribute("availableCourses", availableCourses);

            return "register-courses";
        } else {
            return "redirect:/login-form";
        }
    }

    @GetMapping("/student-info")
    public String showStudentInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Student> studentOptional = studentService.findByUserName(userName);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            model.addAttribute("student", student);
            return "student-info";
        } else {
            return "redirect:/error";
        }
    }

    // MỚI: Hiển thị danh sách sinh viên đăng ký cho khóa học
    @GetMapping("/courses/{courseId}/students")
    public String showStudentsForCourse(@PathVariable Long courseId, Model model) {
        Courses course = coursesService.findById(courseId);

        if (course == null) {
            throw new RuntimeException("Course id not found - " + courseId);
        }

        // Lấy danh sách đăng ký khóa học và tạo danh sách `StudentRegistrationSummary`
        List<StudentRegistrationSummary> studentRegistrations = courseRegistrationService
                .getRegistratedCourses(courseId)
                .stream()
                .map(reg -> new StudentRegistrationSummary(
                        reg.getStudent().getStudentId(),
                        reg.getStudent().getFullName(),
                        courseId,
                        course.getCourseName(),
                        reg.getRegistrationDate(),
                        reg.getStatus() // Cập nhật để lấy `status`
                ))
                .collect(Collectors.toList());

        model.addAttribute("students", studentRegistrations);
        model.addAttribute("course", course);

        return "students";
    }

}
