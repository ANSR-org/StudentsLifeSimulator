package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specialties")
public class Specialty {

    private Long id;
    private String name;
    private University university;
    private Set<Student> students;
    private Set<SpecialtyQuestion> questions;
    private Set<Lecture> lectures;

    public Specialty() {
        this.students = new HashSet<>();
        this.questions = new HashSet<>();
        this.lectures = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @OneToMany(mappedBy = "specialty", targetEntity = Student.class)
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @OneToMany(mappedBy = "specialty", targetEntity = SpecialtyQuestion.class)
    public Set<SpecialtyQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<SpecialtyQuestion> questions) {
        this.questions = questions;
    }

    @OneToMany(mappedBy = "specialty", targetEntity = Lecture.class)
    public Set<Lecture> getLectures() {
        return this.lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }
}
