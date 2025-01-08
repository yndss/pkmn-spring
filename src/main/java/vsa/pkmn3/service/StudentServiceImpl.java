package vsa.pkmn3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import vsa.pkmn3.dao.StudentDao;
import vsa.pkmn3.models.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    @Override
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }

    @Override
    public Student getStudentByFullName(String firstName, String surName, String familyName) {
        return studentDao.findByFullName(firstName, surName, familyName);
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        return studentDao.findStudentsByGroup(group);
    }

    @Override
    public Student saveStudent(Student student) {
        if (studentDao.studentExists(student)) {
            throw new DuplicateKeyException("Student with FIO" + student.getFirstName() + ", " + student.getSurName() + ", " + student.getFamilyName() + " already exists");
        }
        return studentDao.save(student);
    }
}
