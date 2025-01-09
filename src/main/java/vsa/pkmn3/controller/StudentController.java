package vsa.pkmn3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vsa.pkmn3.models.Student;
import vsa.pkmn3.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{group}")
    public List<Student> getStudentsByGroup(@PathVariable String group) {
        return studentService.getStudentsByGroup(group);
    }

    @GetMapping("")
    public Student getStudentByFullName(@RequestBody Student student) {
        return studentService.getStudentByFullName(student.getFirstName(), student.getSurName(), student.getFamilyName());
    }

    @PostMapping("")
    public Student saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
}
