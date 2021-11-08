package com.example.tomasz1452.reports;

import com.example.tomasz1452.event.TaskEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Ta klasa nasłuchuje wydarzeń takich jak togglowanie naszych zadań
 * a także pewnie i dodwanie zadań itp. I rejestruje te wszystki
 * wydarzenia oraz zapisuje je do bazy danych.
 */
@Entity
@Table(name = "task_events")
public class PersistedTaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int taskId;
    String name;
    LocalDateTime occurrence;

    PersistedTaskEvent(){}

    PersistedTaskEvent(TaskEvent source){
        taskId = source.getTaskId();
        name = source.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}
