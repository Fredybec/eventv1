package ma.sir.event.service.impl.admin;

import ma.sir.event.bean.core.EvenementRedis;
import ma.sir.event.ws.dto.BlocOperatoirDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvenementAdminRedisServiceImpl {

    @Autowired
    private RedisTemplate template;


    public EvenementRedis save(EvenementRedis evenement) {
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
}
