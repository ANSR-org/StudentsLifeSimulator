package bg.ansr.simulator.studentsimulatorcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:app.properties", "classpath:local.properties"})
public class StudentSimulatorCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentSimulatorCoreApplication.class, args);
	}
}
