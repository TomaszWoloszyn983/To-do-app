package com.example.tomasz1452.reports;

import com.example.tomasz1452.event.TaskDone;
import com.example.tomasz1452.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class ChangedTaskEventListener {
    public static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    @EventListener
    public void on(TaskDone event){
        logger.info("Got "+ event);
    }

    @EventListener
    public void on(TaskUndone event){
        logger.info("Got "+ event);
    }
}
