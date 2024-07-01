package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.dao.CoursesRepository;
import com.gr1.springboot.mvc.studentmanagement.model.Courses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository coursesRepository;

    @Override
    public List<Courses> findAll() {
        return coursesRepository.findAll();
    }

    @Override
    public Courses findById(Long theId) {

        Optional<Courses> result = coursesRepository.findById(theId);

        Courses theCourses = null;
        if (result.isPresent()) {
            theCourses = result.get();
        }
        else {
            throw new RuntimeException("Did not find courses id: " + theId);
        }

        return theCourses;
    }

    @Override
    public Courses save(Courses theCourses) {
        return coursesRepository.save(theCourses);
    }

    @Override
    public void deleteById(Long theId) {
        coursesRepository.deleteById(theId);
    }

    @Override
    public Long count() {
        return coursesRepository.count();
    }
}
