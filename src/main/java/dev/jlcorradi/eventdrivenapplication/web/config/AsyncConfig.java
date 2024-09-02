package dev.jlcorradi.eventdrivenapplication.web.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor(@Value("${async.corePoolSize:10}") int corePoolSize,
                                 @Value("${async.mazPoolSize:50}") int maxPoolSize,
                                 @Value("${async.queueCapacity:500}") int queueCapacity,
                                 @Value("${spring.threads.virtual.enabled:true}") boolean virtualThreadsEnabled,
                                 Environment env) {
        if (virtualThreadsEnabled) {
            return Executors.newVirtualThreadPerTaskExecutor();
        }

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}
