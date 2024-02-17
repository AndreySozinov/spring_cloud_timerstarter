package ru.savrey;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("timing")
public class TimingProperties {

    /**
     * Включить/выключить замер времени выполнения
     */
    private boolean timing = false;
}
