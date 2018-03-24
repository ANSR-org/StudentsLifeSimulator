package bg.ansr.simulator.studentsimulatorcore.models.lecture;

import java.util.ArrayList;
import java.util.List;

public class ChosenOptionalLectureWrapper {

    private List<ChosenLectureBindingModel> chosenLectures;

    public ChosenOptionalLectureWrapper() {
        this.chosenLectures = new ArrayList<>();
    }

    public List<ChosenLectureBindingModel> getChosenLectures() {
        return this.chosenLectures;
    }

    public void setChosenLectures(List<ChosenLectureBindingModel> chosenLectures) {
        this.chosenLectures = chosenLectures;
    }
}
