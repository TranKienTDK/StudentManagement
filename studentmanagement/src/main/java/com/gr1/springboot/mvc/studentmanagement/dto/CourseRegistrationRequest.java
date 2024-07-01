package com.gr1.springboot.mvc.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistrationRequest {
    private Long studentId;
    private Long courseId;

}
