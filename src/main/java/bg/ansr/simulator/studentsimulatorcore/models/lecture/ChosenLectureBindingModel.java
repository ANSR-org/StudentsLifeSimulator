package bg.ansr.simulator.studentsimulatorcore.models.lecture;

import java.sql.Time;

public class ChosenLectureBindingModel {
    private Long lectureId;
    private String startAt;

    public ChosenLectureBindingModel() {
    }

    public Long getLectureId() {
        return this.lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public String getStartAt() {
        return this.startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }
}
