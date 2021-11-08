package com.example.tomasz1452.reports;

import com.example.tomasz1452.event.TaskDone;
import com.example.tomasz1452.event.TaskEvent;
import com.example.tomasz1452.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class ChangedTaskEventListener {
    public static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);
    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void on(TaskDone event){
//        logger.info("Got "+ event); tego loggera przenieśliśmy do metody onChanged
        onChanged(event);
    }

    @Async
    @EventListener
    public void on(TaskUndone event){
//        logger.info("Got "+ event);
        onChanged(event);
    }

    private void onChanged(final TaskEvent event){
        logger.info("Got "+ event);
        repository.save(new PersistedTaskEvent(event));
    }
}