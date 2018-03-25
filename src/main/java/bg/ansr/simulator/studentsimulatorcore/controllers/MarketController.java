package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.entities.StudentItem;
import bg.ansr.simulator.studentsimulatorcore.entities.Trade;
import bg.ansr.simulator.studentsimulatorcore.models.market.CreateOfferBindingModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.ItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.TradeRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.Optional;

@Controller
@Transactional
@PreAuthorize("isAuthenticated()")
public class MarketController extends BaseController {

    private static final double MAX_DEVIATION = 0.2;

    private final TradeRepository tradeRepository;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final ItemRepository itemRepository;
    private final StudentItemRepository studentItemRepository;

    public MarketController(TradeRepository tradeRepository, StudentService studentService, StudentRepository studentRepository, ItemRepository itemRepository, StudentItemRepository studentItemRepository) {
        this.tradeRepository = tradeRepository;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.itemRepository = itemRepository;
        this.studentItemRepository = studentItemRepository;
    }

    @GetMapping("/market")
    public ModelAndView offers() {
        return this.view(this.tradeRepository.findAll());
    }

    @PostMapping("/market/buy/{id}")
    public ModelAndView buy(@PathVariable Long id) throws Exception {
        Trade trade = this.tradeRepository.findOne(id);
        Student buyer = this.studentService.current();
        if (trade.getPrice().longValue() > buyer.getMoney()) {
            return this.offers();
        }
        Student seller = trade.getStudent();

        seller.setPoints(seller.getPoints() - (trade.getPrice().longValue() / 10));
        seller.setMoney(seller.getMoney() + trade.getPrice().longValue());
        buyer.setPoints(buyer.getPoints() + (trade.getPrice().longValue() / 10));
        buyer.setMoney(buyer.getMoney() - trade.getPrice().longValue());
        StudentItem studentItem = buyer.getItems().stream().filter(i -> i.getItem().getId().equals(trade.getItem().getId()))
                .findFirst()
                .get();
        studentItem.setCount(studentItem.getCount() + trade.getCount());
        this.studentItemRepository.save(studentItem);
        this.studentRepository.save(buyer);
        this.studentRepository.save(seller);
        this.tradeRepository.delete(trade);

        return this.redirect("/market");
    }

    @GetMapping("/market/publish")
    public ModelAndView publish() {
        return this.view(this.studentService.current().getItems());
    }

    @PostMapping("/market/publish")
    public ModelAndView publish(CreateOfferBindingModel offer) throws Exception {
        Student student = this.studentService.current();
        Optional<StudentItem> ownedItemCandidate = student.getItems().stream()
                .filter(i -> i.getItem().getId().equals(offer.getItemId()))
                .findFirst();
        if (!ownedItemCandidate.isPresent()) {
            throw new Exception("Item not owned!");
        }
        StudentItem ownedItem = ownedItemCandidate.get();
        if (ownedItem.getCount() < offer.getCount() || offer.getCount() <= 0) {
            throw new Exception("Cannot sell more than owned or zero!");
        }

        double basePrice = offer.getCount() * ownedItem.getItem().getBasePrice();
        double deviation = Math.abs(basePrice - offer.getPrice());
        if (deviation/basePrice > MAX_DEVIATION) {
            throw new Exception("Cannot sell for more " + MAX_DEVIATION + " deviation");
        }

        ownedItem.setCount(ownedItem.getCount() - offer.getCount());
        this.studentItemRepository.save(ownedItem);

        Trade trade = new Trade();
        trade.setCount(offer.getCount());
        trade.setItem(this.itemRepository.findOne(offer.getItemId()));
        trade.setPrice(offer.getPrice());
        trade.setStudent(student);
        this.tradeRepository.save(trade);

        return this.redirect("/market");
    }
}
