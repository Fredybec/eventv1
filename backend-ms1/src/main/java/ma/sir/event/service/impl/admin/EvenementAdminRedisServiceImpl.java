package ma.sir.event.service.impl.admin;

import ma.sir.event.bean.core.EvenementRedis;
import ma.sir.event.ws.dto.BlocOperatoirDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class EvenementAdminRedisServiceImpl {

  /*  @Autowired
    private RedisTemplate template;*/

   /* public EvenementRedis save(EvenementRedis evenement) {
        if (getBlocOperatoir(evenement) != null)
            template.opsForHash().put(getBlocOperatoirReference(evenement), String.valueOf(evenement.getReference()), evenement);
        return evenement;
    }


    public List<EvenementRedis> findAll(String referenceBloc) {
        return template.opsForHash().values(referenceBloc);
    }

    public EvenementRedis findByReference(String referenceBloc,String reference) {
        return (EvenementRedis) template.opsForHash().get(referenceBloc, reference);
    }

    public int deleteByReference(String referenceBloc,String reference) {
        template.opsForHash().delete(referenceBloc, reference);
        return 1;
    }*/

  /*  private BlocOperatoirDto getBlocOperatoir(EvenementRedis evenement) {
        return evenement.getSalle().getBlocOperatoir();
    }
    private String getBlocOperatoirReference(EvenementRedis evenement) {
        BlocOperatoirDto blocOperatoir = getBlocOperatoir(evenement);
        if (blocOperatoir != null) {
            return blocOperatoir.getReference();
        }
        return null;
    }*/



    private final  Sinks.Many<EvenementRedis> sink;
    @Autowired
    private ReactiveRedisTemplate<String, EvenementRedis> template;

    public EvenementAdminRedisServiceImpl() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void sendEvent(EvenementRedis evenement) {
        sink.tryEmitNext(evenement);
    }


    public Mono<EvenementRedis> save(EvenementRedis evenement) {
        if (getBlocOperatoir(evenement) != null) {
            template.opsForHash()
                    .put(getBlocOperatoirReference(evenement), String.valueOf(evenement.getReference()), evenement)
                    .thenReturn(evenement)
                    .subscribe();

            sink.tryEmitNext(evenement);
        }
        System.out.println("save" + evenement.getSalle().getBlocOperatoir().getReference());

        return Mono.empty();
    }

    public Flux<EvenementRedis> findAll(String referenceBloc) {
        return template.opsForHash().values(referenceBloc)
                .map(object -> (EvenementRedis) object);
    }


    public Mono<EvenementRedis> findByReference(String referenceBloc, String reference) {
        return template.opsForHash().get(referenceBloc, reference)
                .map(obj -> (EvenementRedis) obj)
                .switchIfEmpty(Mono.empty());
    }


    public Mono<Long> deleteByReference(String referenceBloc, String reference) {
        return template.opsForHash().remove(referenceBloc, reference);
    }

    private BlocOperatoirDto getBlocOperatoir(EvenementRedis evenement) {
        return evenement.getSalle().getBlocOperatoir();
    }

    private String getBlocOperatoirReference(EvenementRedis evenement) {
        BlocOperatoirDto blocOperatoir = getBlocOperatoir(evenement);
        if (blocOperatoir != null) {
            return blocOperatoir.getReference();
        }
        return null;
    }




    public Sinks.Many<EvenementRedis> getSink() {
        return sink;
    }
}
