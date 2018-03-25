package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "blocking_events")
public class BlockingEvent {

    private Long id;
    private Student student;
    private Time startedAt;
    private Time endedAt;
    private String lastUrl;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Time getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Time startedAt) {
        this.startedAt = startedAt;
    }

    public Time getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Time endedAt) {
        this.endedAt = endedAt;
    }

    public String getLastUrl() {
        return lastUrl;
    }

    public void setLastUrl(String lastUrl) {
        this.lastUrl = lastUrl;
    }
}
