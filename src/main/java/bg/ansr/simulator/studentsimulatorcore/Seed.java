package bg.ansr.simulator.studentsimulatorcore;

import bg.ansr.simulator.studentsimulatorcore.entities.*;
import bg.ansr.simulator.studentsimulatorcore.repositories.hostel.HostelRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.payment.PaymentPackageRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.specialty.SpecialtyQuestionRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.specialty.SpecialtyRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.ItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.university.UniversityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class Seed implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final ItemRepository itemRepository;
    private final StudentItemRepository studentItemRepository;
    private final UniversityRepository universityRepository;
    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyQuestionRepository questionRepository;
    private final HostelRepository hostelRepository;
    private final PaymentPackageRepository paymentPackageRepository;


    public Seed(StudentRepository studentRepository,
                ItemRepository itemRepository,
                StudentItemRepository studentItemRepository,
                UniversityRepository universityRepository,
                SpecialtyRepository specialtyRepository,
                SpecialtyQuestionRepository questionRepository,
                HostelRepository hostelRepository,
                PaymentPackageRepository paymentPackageRepository) {
        this.studentRepository = studentRepository;
        this.itemRepository = itemRepository;
        this.studentItemRepository = studentItemRepository;
        this.universityRepository = universityRepository;
        this.specialtyRepository = specialtyRepository;
        this.questionRepository = questionRepository;
        this.hostelRepository = hostelRepository;
        this.paymentPackageRepository = paymentPackageRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        if (this.itemRepository.count() <= 0) {
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

        if (this.universityRepository.count() <= 0) {
            University university = new University();
            university.setName("University of World and Local Simulations (UNWLS)");
            Specialty magic = new Specialty();
            magic.setName("Magic Bachelor");
            magic.setUniversity(university);
            SpecialtyQuestion question = new SpecialtyQuestion();
            question.setQuestion("3+2?");
            question.setAnswer("5");
            question.setSpecialty(magic);
            this.universityRepository.save(university);
            this.specialtyRepository.save(magic);
            this.questionRepository.save(question);

            Specialty dev = new Specialty();
            dev.setName("Development Master");
            dev.setUniversity(university);
            SpecialtyQuestion devQuestion = new SpecialtyQuestion();
            devQuestion.setQuestion("System.out.println(4<5);");
            devQuestion.setAnswer("true");
            devQuestion.setSpecialty(dev);
            this.universityRepository.save(university);
            this.specialtyRepository.save(dev);
            this.questionRepository.save(devQuestion);

            Hostel bl23 = new Hostel();
            bl23.setDistance(44L);
            bl23.setName("Block 23");
            bl23.setNoise(8L);
            bl23.setPollution(34L);
            bl23.setRentPrice(400L);
            bl23.setUniversity(university);
            this.hostelRepository.save(bl23);
            this.universityRepository.save(university);

            Hostel bl35 = new Hostel();
            bl35.setDistance(42L);
            bl35.setName("Block 35");
            bl35.setNoise(9L);
            bl35.setPollution(31L);
            bl35.setRentPrice(440L);
            bl35.setUniversity(university);
            this.hostelRepository.save(bl35);
            this.universityRepository.save(university);
        }

        if (this.paymentPackageRepository.count() <= 0) {
            PaymentPackage standard = new PaymentPackage();
            standard.setUSD(5);
            standard.setIngameMoney(2000);
            this.paymentPackageRepository.save(standard);

            PaymentPackage premium = new PaymentPackage();
            premium.setUSD(9.40);
            premium.setIngameMoney(4100);
            this.paymentPackageRepository.save(premium);

            PaymentPackage gold = new PaymentPackage();
            gold.setUSD(39.99);
            gold.setIngameMoney(20000);
            this.paymentPackageRepository.save(gold);
        }
    }
}
