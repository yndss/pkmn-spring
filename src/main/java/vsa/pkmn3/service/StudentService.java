package vsa.pkmn3.service;

import vsa.pkmn3.models.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentByFullName(String firstName, String surName, String familyName);

    List<Student> getStudentsByGroup(String group);

    Student saveStudent(Student student);
}
