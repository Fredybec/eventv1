package ma.sir.event.ws.facade.admin;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.sir.event.bean.core.Evenement;
import ma.sir.event.bean.core.EvenementRedis;
import ma.sir.event.bean.history.EvenementHistory;
import ma.sir.event.dao.criteria.core.EvenementCriteria;
import ma.sir.event.dao.criteria.history.EvenementHistoryCriteria;
import ma.sir.event.dao.facade.core.EvenementDao;
import ma.sir.event.service.facade.admin.EvenementAdminService;
import ma.sir.event.service.impl.admin.EvenementAdminRedisServiceImpl;
import ma.sir.event.ws.converter.EvenementConverter;
import ma.sir.event.ws.dto.EvenementDto;
import ma.sir.event.zynerator.controller.AbstractController;
import ma.sir.event.zynerator.dto.AuditEntityDto;
import ma.sir.event.zynerator.dto.FileTempDto;
import ma.sir.event.zynerator.util.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Api("Manages evenement services")
@RestController
@RequestMapping("/api/admin/evenement/redis/")
public class EvenementRedisRestAdmin  {

    @Autowired
    private EvenementAdminRedisServiceImpl evenementAdminRedisService;

    /*@PostMapping("")
    public EvenementRedis save(@RequestBody EvenementRedis evenement) {
        return evenementAdminRedisService.save(evenement);
    }

    @GetMapping("/bloc-operatoire/{referenceBloc}")
    public List<EvenementRedis> findAll(@PathVariable String referenceBloc) {
        return evenementAdminRedisService.findAll(referenceBloc);
    }

    @GetMapping("/bloc-operatoire/{referenceBloc}/reference/{reference}")
    public EvenementRedis findByReference(@PathVariable String referenceBloc,@PathVariable String reference) {
        return evenementAdminRedisService.findByReference(referenceBloc,reference);
    }
    @DeleteMapping("/bloc-operatoire/{referenceBloc}/reference/{reference}")
    public int deleteByReference(@PathVariable String referenceBloc,@PathVariable String reference)   {
        return evenementAdminRedisService.deleteByReference(referenceBloc,reference);
    }*/


    @PostMapping("")
    public Mono<EvenementRedis> save(@RequestBody EvenementRedis evenement) {
        return evenementAdminRedisService.save(evenement);
    }

    @GetMapping("/bloc-operatoire/{referenceBloc}")
    public Flux<EvenementRedis> findAll(@PathVariable String referenceBloc) {
        return evenementAdminRedisService.findAll(referenceBloc);
    }

    @GetMapping("/bloc-operatoire/{referenceBloc}/reference/{reference}")
    public Mono<EvenementRedis> findByReference(@PathVariable String referenceBloc, @PathVariable String reference) {
        return evenementAdminRedisService.findByReference(referenceBloc, reference);
    }

    @DeleteMapping("/bloc-operatoire/{referenceBloc}/reference/{reference}")
    public Mono<Long> deleteByReference(@PathVariable String referenceBloc, @PathVariable String reference) {
        return evenementAdminRedisService.deleteByReference(referenceBloc, reference);
    }


}