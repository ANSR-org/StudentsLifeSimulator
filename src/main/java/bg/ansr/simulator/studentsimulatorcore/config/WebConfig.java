package bg.ansr.simulator.studentsimulatorcore.config;

import bg.ansr.simulator.studentsimulatorcore.interceptors.BlockingEventsInterceptor;
import bg.ansr.simulator.studentsimulatorcore.interceptors.LifecycleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private final BlockingEventsInterceptor blockingEventsInterceptor;
    private final LifecycleInterceptor lifecycleInterceptor;

    public WebConfig(BlockingEventsInterceptor blockingEventsInterceptor, LifecycleInterceptor lifecycleInterceptor) {
        this.blockingEventsInterceptor = blockingEventsInterceptor;
        this.lifecycleInterceptor = lifecycleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.blockingEventsInterceptor);
        registry.addInterceptor(this.lifecycleInterceptor);
    }
}

