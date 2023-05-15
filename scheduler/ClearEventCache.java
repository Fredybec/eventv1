package ma.sir.event.scheduler;

import lombok.extern.slf4j.Slf4j;

import ma.sir.event.redis_webflux.service.EventRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import ma.sir.event.bean.core.EvenementRedis;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@EnableScheduling
public class ClearEventCache {
    @Autowired
    private EventRedisServiceImpl eventService;
    @Scheduled(cron = "00 58 16 * * *")
    public void cleanOldEventsFromCache() {
        final boolean[] isOlderEvent = new boolean[1];
        final LocalDate[] extractedEventDate = new LocalDate[1];
        Flux<EvenementRedis> eventFlux = eventService.findAll();
        eventFlux.collectList().subscribe(olderEvents -> {
            if (!olderEvents.isEmpty()) {
                for (EvenementRedis event : olderEvents) {
                    extractedEventDate[0] = extractAndConvertToLocalDate(event.getReference());
                    isOlderEvent[0] = compareDates(extractedEventDate[0]);
                    deleteOldEventFromCache(isOlderEvent[0], event);
                    logDeletingStatus(isOlderEvent[0], event);
                }
            }
        });

    }
    public LocalDate extractAndConvertToLocalDate(String eventReference){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String extractedDate = eventReference.split("_")[3];
        return LocalDate.parse(extractedDate, formatter);
    }
    public boolean compareDates(LocalDate eventDate){
        return eventDate.isBefore(LocalDate.now());
    }
    public void deleteOldEventFromCache(boolean isOlderEvent, EvenementRedis event){
        if(isOlderEvent) {
            eventService.deleteByReference(event.getReference()).subscribe();
        }
    }
    public void logDeletingStatus(boolean isComparesDatesReturnsTrue, EvenementRedis event){
        if (isComparesDatesReturnsTrue) {
            log.info("Event with reference "+event.getReference()+ " deleted with success");
        }
    }
}
