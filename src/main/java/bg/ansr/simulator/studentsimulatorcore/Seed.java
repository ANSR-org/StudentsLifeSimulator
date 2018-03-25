package bg.ansr.simulator.studentsimulatorcore;

import bg.ansr.simulator.studentsimulatorcore.entities.*;
import bg.ansr.simulator.studentsimulatorcore.repositories.hostel.HostelRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.lecture.LectureRepository;
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
import java.sql.Time;

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
    private final LectureRepository lectureRepository;


    public Seed(StudentRepository studentRepository,
                ItemRepository itemRepository,
                StudentItemRepository studentItemRepository,
                UniversityRepository universityRepository,
                SpecialtyRepository specialtyRepository,
                SpecialtyQuestionRepository questionRepository,
                HostelRepository hostelRepository,
                PaymentPackageRepository paymentPackageRepository,
                LectureRepository lectureRepository) {
        this.studentRepository = studentRepository;
        this.itemRepository = itemRepository;
        this.studentItemRepository = studentItemRepository;
        this.universityRepository = universityRepository;
        this.specialtyRepository = specialtyRepository;
        this.questionRepository = questionRepository;
        this.hostelRepository = hostelRepository;
        this.paymentPackageRepository = paymentPackageRepository;
        this.lectureRepository = lectureRepository;
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
            SpecialtyQuestion question1 = new SpecialtyQuestion();
            question1.setQuestion("3+3?");
            question1.setAnswer("6");
            question1.setSpecialty(magic);
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

        if (this.lectureRepository.count() <= 0) {
            Specialty specialty = this.specialtyRepository.findOne(1L);
            Lecture lecture = new Lecture();
            lecture.setName("Math for Magicians");
            lecture.setStartedAt(Time.valueOf("09:00:00"));
            lecture.setEndedAt(Time.valueOf("09:00:15"));
            lecture.setEnergyCost(10L);
            lecture.setMandatory(true);
            lecture.setMoneyProfit(20L);
            lecture.setSpecialty(specialty);
            this.lectureRepository.save(lecture);
            specialty.getLectures().add(lecture);

            Lecture lecture1 = new Lecture();
            lecture1.setName("Magic Science");
            lecture1.setStartedAt(Time.valueOf("11:00:00"));
            lecture1.setEndedAt(Time.valueOf("11:01:00"));
            lecture1.setEnergyCost(20L);
            lecture1.setMandatory(true);
            lecture1.setMoneyProfit(10L);
            lecture1.setSpecialty(specialty);
            this.lectureRepository.save(lecture1);
            specialty.getLectures().add(lecture1);

            Lecture lecture2 = new Lecture();
            lecture2.setName("Deep Magic");
            lecture2.setStartedAt(Time.valueOf("15:00:00"));
            lecture2.setEndedAt(Time.valueOf("15:01:00"));
            lecture2.setEnergyCost(30L);
            lecture2.setMandatory(false);
            lecture2.setMoneyProfit(30L);
            lecture2.setSpecialty(specialty);
            this.lectureRepository.save(lecture2);
            specialty.getLectures().add(lecture2);

            Lecture lecture12 = new Lecture();
            lecture12.setName("Magic Algorithms");
            lecture12.setStartedAt(Time.valueOf("15:02:00"));
            lecture12.setEndedAt(Time.valueOf("15:03:00"));
            lecture12.setEnergyCost(30L);
            lecture12.setMandatory(false);
            lecture12.setMoneyProfit(30L);
            lecture12.setSpecialty(specialty);
            this.lectureRepository.save(lecture12);
            specialty.getLectures().add(lecture12);

            this.specialtyRepository.save(specialty);


            Specialty specialty2 = this.specialtyRepository.findOne(2L);
            Lecture lecture4 = new Lecture();
            lecture4.setName("Math for Developers");
            lecture4.setStartedAt(Time.valueOf("09:00:00"));
            lecture4.setEndedAt(Time.valueOf("09:00:15"));
            lecture4.setEnergyCost(10L);
            lecture4.setMandatory(true);
            lecture4.setMoneyProfit(20L);
            lecture4.setSpecialty(specialty2);
            this.lectureRepository.save(lecture4);
            specialty2.getLectures().add(lecture4);

            Lecture lecture5 = new Lecture();
            lecture5.setName("Data Science");
            lecture5.setStartedAt(Time.valueOf("11:00:00"));
            lecture5.setEndedAt(Time.valueOf("11:01:00"));
            lecture5.setEnergyCost(20L);
            lecture5.setMandatory(true);
            lecture5.setMoneyProfit(10L);
            lecture5.setSpecialty(specialty2);
            this.lectureRepository.save(lecture5);
            specialty2.getLectures().add(lecture5);

            Lecture lecture6 = new Lecture();
            lecture6.setName("Deep Learning");
            lecture6.setStartedAt(Time.valueOf("15:00:00"));
            lecture6.setEndedAt(Time.valueOf("15:01:00"));
            lecture6.setEnergyCost(30L);
            lecture6.setMandatory(false);
            lecture6.setMoneyProfit(30L);
            lecture6.setSpecialty(specialty2);
            this.lectureRepository.save(lecture6);
            specialty2.getLectures().add(lecture6);

            Lecture lecture16 = new Lecture();
            lecture16.setName("Algorithms");
            lecture16.setStartedAt(Time.valueOf("15:02:00"));
            lecture16.setEndedAt(Time.valueOf("15:03:00"));
            lecture16.setEnergyCost(30L);
            lecture16.setMandatory(false);
            lecture16.setMoneyProfit(30L);
            lecture16.setSpecialty(specialty2);
            this.lectureRepository.save(lecture16);
            specialty2.getLectures().add(lecture16);


            this.specialtyRepository.save(specialty2);
        }
    }
}
