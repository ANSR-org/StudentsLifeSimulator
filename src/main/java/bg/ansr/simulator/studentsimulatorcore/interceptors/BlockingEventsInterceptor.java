package bg.ansr.simulator.studentsimulatorcore.interceptors;

import bg.ansr.simulator.studentsimulatorcore.entities.BlockingEvent;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class BlockingEventsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StudentService studentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))) {
            Set<BlockingEvent> blockingEvents = studentService.current().getBlockingEvents();
            if(!blockingEvents.isEmpty()) {
                response.sendRedirect(blockingEvents.iterator().next().getLastUrl());
            }
        }

        return true;
    }
}
