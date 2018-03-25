package bg.ansr.simulator.studentsimulatorcore.interceptors;

import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Transactional
public class LifecycleInterceptor extends HandlerInterceptorAdapter {

    private static final int TWO_MINUTES = 120;
    private static final int ONE_HOUR = 3600;

    private final StudentService studentService;

    public LifecycleInterceptor(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return true;
        }

        Student student = studentService.current();

        long seconds = student.getLastGivenIncome().until(LocalDateTime.now(), ChronoUnit.SECONDS);
        if (seconds >= TWO_MINUTES) {
            double ratio = seconds / ONE_HOUR;
            long currentIncome = ((Double)(student.getIncomePerHour() * ratio)).longValue();
            student.setMoney(student.getMoney() + currentIncome);
            student.setLastGivenIncome(LocalDateTime.now());
            this.studentService.save(student);
        }

        return true;
    }
}
