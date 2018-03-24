package bg.ansr.simulator.studentsimulatorcore.models.lecture;

import java.sql.Time;

public class ChosenLectureBindingModel {
    private Long lectureId;
    private Time startAt;
    private Time endAt;

    public ChosenLectureBindingModel() {
    }

    public Long getLectureId() {
        return this.lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public Time getStartAt() {
        return this.startAt;
    }

    public void setStartAt(Time startAt) {
        this.startAt = startAt;
    }

    public Time getEndAt() {
        return this.endAt;
    }

    public void setEndAt(Time endAt) {
        this.endAt = endAt;
    }
}
