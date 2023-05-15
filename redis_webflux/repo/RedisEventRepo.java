package ma.sir.event.redis_webflux.repo;

import lombok.RequiredArgsConstructor;
import ma.sir.event.bean.core.EvenementRedis;
import ma.sir.event.redis_webflux.conf.ObjectMapperUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static ma.sir.event.redis_webflux.conf.ObjectMapperUtils.HASH_KEY;

@Repository
@RequiredArgsConstructor
public class RedisEventRepo implements EventRepo {

    private final ReactiveRedisComponent reactiveRedisComponent;

    @Override
    public Mono<EvenementRedis> save(EvenementRedis event) {
        return reactiveRedisComponent.set(HASH_KEY, event.getReference(), event).map(b -> event);
    }

    @Override
    public Mono<EvenementRedis> findByReference(String reference) {
        return reactiveRedisComponent.get(HASH_KEY, reference).flatMap(d -> Mono.just(ObjectMapperUtils.objectMapper(d, EvenementRedis.class)));
    }

    @Override
    public Flux<EvenementRedis> findAll(){
        return reactiveRedisComponent.get(HASH_KEY).map(b -> ObjectMapperUtils.objectMapper(b, EvenementRedis.class))
                .collectList().flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Long> delete(String reference) {
        return reactiveRedisComponent.remove(HASH_KEY,reference);
    }
}
