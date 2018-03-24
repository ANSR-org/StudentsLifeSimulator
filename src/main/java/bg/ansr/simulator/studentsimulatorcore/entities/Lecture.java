package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lectures")
public class Lecture {

    private Long id;
    private String name;
    private Time startedAt;
    private Time endedAt;
    private Specialty specialty;
    private Set<Student> students;
    private Long energyCost;
    private boolean isMandatory;
    private Set<Schedule> schedules;

    public Lecture() {
        this.students = new HashSet<>();
        this.schedules = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Time getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(Time startedAt) {
        this.startedAt = startedAt;
    }

    public Time getEndedAt() {
        return this.endedAt;
    }

    public void setEndedAt(Time endedAt) {
        this.endedAt = endedAt;
    }

    @ManyToOne
    public Specialty getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @OneToMany(mappedBy = "currentLecture", targetEntity = Student.class)
    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Long getEnergyCost() {
        return this.energyCost;
    }

    public void setEnergyCost(Long energyCost) {
        this.energyCost = energyCost;
    }

    @Column(columnDefinition = "TINYINT DEFAULT TRUE")
    public boolean isMandatory() {
        return this.isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    @OneToMany(mappedBy = "lecture", targetEntity = Schedule.class)
    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
