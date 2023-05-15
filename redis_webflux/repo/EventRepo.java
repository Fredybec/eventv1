package ma.sir.event.redis_webflux.repo;

import ma.sir.event.bean.core.EvenementRedis;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface EventRepo {

        Mono<EvenementRedis> save(EvenementRedis evenementRedis);

        Mono<EvenementRedis> findByReference(String ref);

        Flux<EvenementRedis> findAll();

        Mono<Long> delete(String id);

}
