package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.dao.AccountRepository;
import com.gr1.springboot.mvc.studentmanagement.dao.StudentRepository;
import com.gr1.springboot.mvc.studentmanagement.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private AccountRepository accountRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository theStudentRepository, AccountRepository theAccountRepository) {
        studentRepository = theStudentRepository;
        accountRepository = theAccountRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long theId) {

        Optional<Student> result = studentRepository.findById(theId);

        Student theStudent = null;
        if (result.isPresent()) {
            theStudent = result.get();
        }
        else {
            throw new RuntimeException("Did not find student id - " + theId);
        }

        return theStudent;
    }

    @Override
    @Transactional
    public Student save(Student theStudent) {
        // Kiểm tra xem student có account không
        if (theStudent.getAccount() != null) {
            // Lưu account trước
            accountRepository.save(theStudent.getAccount());
        }
        // Lưu student
        return studentRepository.save(theStudent);
    }


    @Override
    public void deleteById(Long theId) {
        studentRepository.deleteById(theId);
    }

    @Override
    public Optional<Student> findByUserName(String userName) {
        return studentRepository.findByAccount_UserName(userName);
    }

    @Override
    public Long count() {
        return studentRepository.count();
    }

}
