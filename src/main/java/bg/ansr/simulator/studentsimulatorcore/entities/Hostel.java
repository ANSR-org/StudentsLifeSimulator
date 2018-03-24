package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Hostel {

    private Long id;
    private String name;
    private Long pollution;
    private Long noise;
    private Long distance;
    private Set<Student> students;
    private University university;

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

    public Long getPollution() {
        return pollution;
    }

    public void setPollution(Long pollution) {
        this.pollution = pollution;
    }

    public Long getNoise() {
        return noise;
    }

    public void setNoise(Long noise) {
        this.noise = noise;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    @OneToMany(mappedBy = "hostel", targetEntity = Student.class)
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @ManyToOne
    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
