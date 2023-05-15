package ma.sir.event.redis_webflux.service;

import lombok.RequiredArgsConstructor;
import ma.sir.event.bean.core.EvenementRedis;
import ma.sir.event.redis_webflux.repo.EventRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EventRedisServiceImpl implements EventRedisService{

    private final EventRepo eventRepo;

    @Override
    public Mono<EvenementRedis> save(EvenementRedis book) {
        return eventRepo.save(book);
    }

    @Override
    public Flux<EvenementRedis> findAll(){
        return eventRepo.findAll();
    }

    @Override
    public Mono<EvenementRedis> findByReference(String ref){
        return eventRepo.findByReference(ref);
    }

    @Override
    public Mono<Long> deleteByReference(String ref) {
        return eventRepo.delete(ref);
    }

}

