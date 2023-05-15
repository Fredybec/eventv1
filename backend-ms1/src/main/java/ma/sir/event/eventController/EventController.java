package ma.sir.event.eventController;

import ma.sir.event.bean.core.EvenementRedis;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
public class EventController {

    private final Sinks.Many<EvenementRedis> sink;

    public EventController() {
        this.sink = Sinks.many().unicast().onBackpressureBuffer();
    }

    @GetMapping(value = "event/stream/new/{referenceBloc}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<EvenementRedis>> streamEvents(@PathVariable String referenceBloc) {
        return sink.asFlux()
                .map(e -> ServerSentEvent.<EvenementRedis>builder()
                        .id(e.getReference())
                        .event(e.getDescription())
                        .data(e)
                        .build());
    }

    public void sendEvent(EvenementRedis evenement) {
        sink.tryEmitNext(evenement);
    }
}

