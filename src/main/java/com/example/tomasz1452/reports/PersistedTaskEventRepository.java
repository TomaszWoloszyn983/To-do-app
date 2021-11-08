package com.example.tomasz1452.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Klasa zwraca wszystkie wydarzenia związane z taskiem o padanaym Id.
 */
interface PersistedTaskEventRepository extends JpaRepository<PersistedTaskEvent, Integer> {
    List<PersistedTaskEvent> findByTaskId(int taskId);
}
