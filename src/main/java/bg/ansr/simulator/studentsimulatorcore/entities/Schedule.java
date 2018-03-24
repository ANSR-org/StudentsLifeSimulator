package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "schedules")
public class Schedule {
    private Long id;
    private Lecture lecture;
    private Student subscribedStudent;
    private Time startedAt;
    private Time endedAt;

    public Schedule() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Lecture getLecture() {
        return this.lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    @ManyToOne
    public Student getSubscribedStudent() {
        return this.subscribedStudent;
    }

    public void setSubscribedStudent(Student subscribedStudent) {
        this.subscribedStudent = subscribedStudent;
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
}
