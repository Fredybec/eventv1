package ma.sir.event.SinkConfig;

import ma.sir.event.bean.core.EvenementRedis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class EventSinkConfig {

    @Bean
    public Map<String, Sinks.Many<EvenementRedis>> sinksByBlocReference() {
        return new ConcurrentHashMap<>();
    }

}
