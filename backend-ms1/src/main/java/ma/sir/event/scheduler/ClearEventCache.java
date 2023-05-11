package ma.sir.event.scheduler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ma.sir.event.bean.core.EvenementRedis;
import ma.sir.event.service.impl.admin.EvenementAdminRedisServiceImpl;
@Slf4j
@Component
@EnableScheduling
public class ClearEventCache {

    @Autowired
    private EvenementAdminRedisServiceImpl eventService;
    @Scheduled(cron = "00 17 18 * * *")
    public void cleanOldEventsFromCache() {
        boolean isOlderEvent ;
        LocalDate extractedEventDate;
        List<EvenementRedis> olderEvents = eventService.findAll();
        if (!olderEvents.isEmpty()){
            for (EvenementRedis event : olderEvents) {
                extractedEventDate = extractAndConvertToLocalDate(event.getReference());
                isOlderEvent  = compareDates(extractedEventDate);
                deleteOldEventFromCache(isOlderEvent , event);
                log.info("Event with reference "+event.getReference()+ " deleted with success");
            }
        }
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
            eventService.deleteByReference(event.getReference());
        }
    }
}
