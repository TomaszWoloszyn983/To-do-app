package com.example.tomasz1452;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskGroup;
import com.example.tomasz1452.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

    /*
    W tej klasie ćwiczymy użycie ApplicationListenera. Czyli cos podobnego do
    ActionListenera z Javy, czyli dokładniej reakcje na jakieś wydzarzenie które
    miało miejsce w aplikacji. W tym przypadku chodzi chyba o odświerzenie aplikacji
    z tego co sam sie domyślam z nazwy ContextRefreshedEvent.
    Naszą reakcją na odświerzenie jest dodanie nowej grupy zadań wraz z czterema
    podanymi przez nas zadaniami w tej grupie.

    Wydarzenie do nasłuchiwania może być też wywołanie jakiejś metody itp.
    W celu przećwiczenia tego tworzymy pakiet event.
     */

@Component
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);
    private final TaskGroupRepository groupRepository;

    Warmup(final TaskGroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");
        final String description = "ApplicationContextEvent";
        if(!groupRepository.existsByDescription(description)){
            logger.info("No required group found! Adding it!");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(
                    new Task("ContextClosedEvent", null, group),
                    new Task("ContextRefreshedEvent", null, group),
                    new Task("ContextStoppedEvent", null, group),
                    new Task("ContextStartedEvent", null, group)
            ));
            groupRepository.save(group);
        }
    }
}
