package bg.ansr.simulator.studentsimulatorcore.services.student;

import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
