package com.gr1.springboot.mvc.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegistrationSummary {

    // Getter và Setter
    // Các trường dữ liệu
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;
    private Date registrationDate;
    private String status;


}
