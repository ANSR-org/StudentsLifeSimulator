package bg.ansr.simulator.studentsimulatorcore.models.specialty;

import java.util.HashMap;
import java.util.Map;

public class ChooseSpecialtyBindingModel {
    private Map<Long, String> answers;

    public ChooseSpecialtyBindingModel() {
        this.answers = new HashMap<>();
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}
