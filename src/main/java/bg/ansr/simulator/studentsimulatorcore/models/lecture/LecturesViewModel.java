package bg.ansr.simulator.studentsimulatorcore.models.lecture;

import bg.ansr.simulator.studentsimulatorcore.entities.Lecture;

import java.util.List;

public class LecturesViewModel {

    private List<Lecture> mandatoryLectures;
    private List<Lecture> optionalLectures;

    public LecturesViewModel() {
    }

    public List<Lecture> getMandatoryLectures() {
        return this.mandatoryLectures;
    }

    public void setMandatoryLectures(List<Lecture> mandatoryLectures) {
        this.mandatoryLectures = mandatoryLectures;
    }

    public List<Lecture> getOptionalLectures() {
        return this.optionalLectures;
    }

    public void setOptionalLectures(List<Lecture> optionalLectures) {
        this.optionalLectures = optionalLectures;
    }
}
