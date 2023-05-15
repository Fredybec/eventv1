package ma.sir.event.redis_webflux.ws;

import ma.sir.event.bean.core.EvenementRedis;
import ma.sir.event.redis_webflux.service.EventRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RedisEventController {


    @Autowired
    private EventRedisServiceImpl eventRedisService;

    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EvenementRedis> save(@RequestBody  EvenementRedis event) {
        return eventRedisService.save(event);
    }

    @GetMapping("/event")
    public Flux<EvenementRedis> findAll() {
        return eventRedisService.findAll();
    }

    @GetMapping("/event/{ref}")
    public Mono<EvenementRedis> findByReference(@PathVariable String ref) {
        return eventRedisService.findByReference(ref);
    }

    @DeleteMapping("/event/{ref}")
    public Mono<Long> deleteByReference(@PathVariable String ref) {
        return eventRedisService.deleteByReference(ref);
    }

}