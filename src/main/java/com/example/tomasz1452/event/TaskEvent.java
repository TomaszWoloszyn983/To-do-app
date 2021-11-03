package com.example.tomasz1452.event;

import com.example.tomasz1452.model.Task;

import java.time.Clock;
import java.time.Instant;

public abstract class TaskEvent {

    public static TaskEvent changed(Task source){
        return source.isDone() ? new TaskDone(source) : new TaskUndone(source);

    }

    private int taskId;
    private Instant occurence;

    TaskEvent(final int taskId, Clock clock){
        this.taskId = taskId;
        this.occurence = Instant.now(clock);
            /*
            occurance jest czasem wykonania się wydarzenia.
            Pobierając ten czas za pomocą klasy Clock możemy
            go wyświetlić i uzyć w metodzie.
            Czy jakoś tak.
             */
    }

    int getTaskId() {
        return taskId;
    }

    Instant getOccurence() {
        return occurence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+ " {" +
                "taskId=" + taskId +
                ", occurence=" + occurence +
                '}';
    }
}
