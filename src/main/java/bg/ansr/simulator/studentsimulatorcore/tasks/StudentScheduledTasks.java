package bg.ansr.simulator.studentsimulatorcore.tasks;

import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.entities.StudentItem;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Component
@Transactional
public class StudentScheduledTasks {

    private static final Long SCHEDULED_ITEMS_BONUS = 5L;

    private final StudentItemRepository studentItemRepository;

    public StudentScheduledTasks(StudentItemRepository studentItemRepository) {
        this.studentItemRepository = studentItemRepository;
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void updateItems() {
        for (StudentItem studentItem : this.studentItemRepository.findItemsForScheduledUpdate()) {
            studentItem.setLastUpdate(new Date());
            studentItem.setCount(studentItem.getCount() + SCHEDULED_ITEMS_BONUS);
            Student student = studentItem.getStudent();
            student.setPoints(student.getPoints() + (long)((studentItem.getCount() * studentItem.getItem().getBasePrice()) / 10));
            this.studentItemRepository.save(studentItem);
        }
    }

}
