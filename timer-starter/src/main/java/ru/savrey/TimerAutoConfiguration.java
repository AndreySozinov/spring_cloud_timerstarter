package ru.savrey;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.savrey.timing.TimerAspect;

@Configuration
@EnableConfigurationProperties(TimingProperties.class)
@ConditionalOnProperty(value = "timing.enabled", havingValue = "true")
public class TimerAutoConfiguration {

    @Bean
    TimerAspect timerAspect(TimingProperties properties) {
        return new TimerAspect(properties);
    }
}
