package ma.sir.event.redis_webflux.service;



import ma.sir.event.bean.core.EvenementRedis;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRedisService {

    Mono<EvenementRedis> save(EvenementRedis event);

    Flux<EvenementRedis> findAll();

    Mono<EvenementRedis> findByReference(String reference);

    Mono<Long> deleteByReference(String reference);
}