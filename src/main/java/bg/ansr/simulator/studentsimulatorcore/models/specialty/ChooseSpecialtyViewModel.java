package bg.ansr.simulator.studentsimulatorcore.models.specialty;

import bg.ansr.simulator.studentsimulatorcore.entities.SpecialtyQuestion;

import java.util.Map;
import java.util.Set;

public class ChooseSpecialtyViewModel {
    private ChooseSpecialtyBindingModel mapHolder;
    private Set<SpecialtyQuestion> questions;

    public ChooseSpecialtyViewModel() {

    }

    public ChooseSpecialtyViewModel(ChooseSpecialtyBindingModel mapHolder, Set<SpecialtyQuestion> questions) {
        this.mapHolder = mapHolder;
        this.questions = questions;
    }

    public ChooseSpecialtyBindingModel getMapHolder() {
        return mapHolder;
    }

    public void setMapHolder(ChooseSpecialtyBindingModel mapHolder) {
        this.mapHolder = mapHolder;
    }

    public Set<SpecialtyQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<SpecialtyQuestion> questions) {
        this.questions = questions;
    }
}
