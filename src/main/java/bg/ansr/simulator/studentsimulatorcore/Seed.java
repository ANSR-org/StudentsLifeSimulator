package bg.ansr.simulator.studentsimulatorcore;

import bg.ansr.simulator.studentsimulatorcore.entities.Item;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.ItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class Seed implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final ItemRepository itemRepository;
    private final StudentItemRepository studentItemRepository;

    public Seed(StudentRepository studentRepository, ItemRepository itemRepository, StudentItemRepository studentItemRepository) {
        this.studentRepository = studentRepository;
        this.itemRepository = itemRepository;
        this.studentItemRepository = studentItemRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        if (this.itemRepository.count() > 0) {
            return;
        }

        Item itemPickle = new Item();
        itemPickle.setName("Pickle");
        itemPickle.setBasePrice(80.0);
        this.itemRepository.save(itemPickle);

        Item itemCompote = new Item();
        itemCompote.setName("Compote");
        itemCompote.setBasePrice(64.0);
        this.itemRepository.save(itemCompote);

        Item itemSausage = new Item();
        itemSausage.setName("Sausage");
        itemSausage.setBasePrice(37.0);
        this.itemRepository.save(itemSausage);
    }
}
