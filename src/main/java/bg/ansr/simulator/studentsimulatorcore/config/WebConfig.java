package bg.ansr.simulator.studentsimulatorcore.config;

import bg.ansr.simulator.studentsimulatorcore.interceptors.BlockingEventsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    BlockingEventsInterceptor blockingEventsInterceptor() {
        return new BlockingEventsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blockingEventsInterceptor());
    }
}

