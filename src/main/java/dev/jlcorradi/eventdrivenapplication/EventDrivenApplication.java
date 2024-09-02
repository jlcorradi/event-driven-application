package dev.jlcorradi.eventdrivenapplication;

import dev.jlcorradi.eventdrivenapplication.core.events.OperationCrudEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

@Slf4j
@SpringBootApplication
public class EventDrivenApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EventDrivenApplication.class);
        springApplication.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
            ConfigurableEnvironment environment = event.getEnvironment();
            log.info("""
                    
                    ---> Starting application with the following context configuration <---
                    Log Path: {}
                    Port: {}
                    
                    """, environment.getProperty("port"), environment.getProperty("logging.file.path"));
        });
        springApplication.run(args);
    }

    @Bean
    public CommandLineRunner init(List<OperationCrudEventHandler> listeners) {
        return args -> {
            for (OperationCrudEventHandler listener : listeners) {
                log.info("listener");
            }
        };
    }
}
