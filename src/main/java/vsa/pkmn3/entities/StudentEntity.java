package vsa.pkmn3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vsa.pkmn3.models.Student;

import java.util.UUID;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "\"first_name\"")
    private String firstName;

    @Column(name = "\"sur_name\"")
    private String surName;

    @Column(name = "\"family_name\"")
    private String familyName;

    @Column(name = "\"group\"")
    private String group;

    @OneToOne(mappedBy = "pokemonOwner")
    private CardEntity card;

    public static StudentEntity convertFromStudent(Student student) {
        StudentEntity.StudentEntityBuilder builder = StudentEntity.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .surName(student.getSurName())
                .familyName(student.getFamilyName())
                .group(student.getGroup());

        return builder.build();
    }
}
