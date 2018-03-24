package bg.ansr.simulator.studentsimulatorcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:app.properties", "classpath:local.properties"})
public class StudentSimulatorCoreApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StudentSimulatorCoreApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<StudentSimulatorCoreApplication> applicationClass = StudentSimulatorCoreApplication.class;
}
