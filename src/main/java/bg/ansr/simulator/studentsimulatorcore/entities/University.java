package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class University {

    private Long id;
    private String name;
    private Set<Specialty> specialties;
    private Set<Student> students;
    private Set<Hostel> hostels;

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

    @OneToMany(mappedBy = "university", targetEntity = Specialty.class)
    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    @OneToMany(mappedBy = "university", targetEntity = Student.class)
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @OneToMany(mappedBy = "university", targetEntity = Hostel.class)
    public Set<Hostel> getHostels() {
        return hostels;
    }

    public void setHostels(Set<Hostel> hostels) {
        this.hostels = hostels;
    }
}
