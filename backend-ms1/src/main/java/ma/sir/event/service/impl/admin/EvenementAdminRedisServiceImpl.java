package ma.sir.event.service.impl.admin;

import ma.sir.event.bean.core.EvenementRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvenementAdminRedisServiceImpl {

    public static final String HASH_KEY = "Evenement";
    @Autowired
    private RedisTemplate template;

    public EvenementRedis save(EvenementRedis evenement) {
        template.opsForHash().put(HASH_KEY, String.valueOf(evenement.getReference()), evenement);
        return evenement;
    }

    public List<EvenementRedis> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public EvenementRedis findByReference(String reference) {
        return (EvenementRedis) template.opsForHash().get(HASH_KEY, reference);
    }

    public int deleteByReference(String reference) {
        template.opsForHash().delete(HASH_KEY, reference);
        return 1;
    }
}
