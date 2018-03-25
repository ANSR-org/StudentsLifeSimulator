package bg.ansr.simulator.studentsimulatorcore.interceptors;

import bg.ansr.simulator.studentsimulatorcore.entities.BlockingEvent;
import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.repositories.blockingEvent.BlockingEventRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@Component
public class BlockingEventsInterceptor extends HandlerInterceptorAdapter {

    private final StudentService studentService;
    private final BlockingEventRepository blockingEventRepository;

    public BlockingEventsInterceptor(StudentService studentService, BlockingEventRepository blockingEventRepository) {
        this.studentService = studentService;
        this.blockingEventRepository = blockingEventRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().equals("/error")) {
            return false;
        }

        Authentication logedUser =SecurityContextHolder.getContext().getAuthentication();
        Object princ = logedUser.getPrincipal();
        if("anonymousUser".equals(princ)) {
            return true;
        }

        Student student = studentService.current();
        BlockingEvent event = blockingEventRepository.findFirstByStudentAndStartedAtBeforeAndEndedAtAfterOrderByStartedAtDesc(student,
                LocalDateTime.now(), LocalDateTime.now());
        if(event != null) {
            student.setLastGivenIncome(event.getEndedAt());
            response.sendRedirect(event.getLastUrl());
            return false;
        }

        if (student.getCurrentLecture() != null && (blockingEventRepository.countAllByStudentAndStartedAtAfter(student, LocalDateTime.now()) == 0)) {
            student.setIncomePerHour(student.getIncomePerHour() + student.getCurrentLecture().getMoneyProfit());
            student.setCurrentLecture(null);
        }

        return true;
    }
}
