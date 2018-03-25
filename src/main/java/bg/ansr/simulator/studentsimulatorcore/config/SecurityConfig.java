package bg.ansr.simulator.studentsimulatorcore.config;

import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final StudentService studentService;

    public SecurityConfig(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .and()
                .antMatcher("/**").anonymous()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/university/choose")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .csrf().disable()
                .userDetailsService(this.studentService);
    }
}
