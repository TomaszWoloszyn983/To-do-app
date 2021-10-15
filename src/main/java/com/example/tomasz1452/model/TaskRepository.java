package com.example.tomasz1452.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.net.ContentHandler;
import java.util.List;
import java.util.Optional;

/**
 * Definiujemy tutaj metody, które będą nam potrzebne do wysyłania żądań
 * do serwera.
 * TaskRepository jest interfacem dlatego implemetując go uzyskujemy
 * dostęp do jego metod które są abstrakcyjne.
 *
 * Co okazało się ważne, należy zachować odpowiednią kolejność
 * przy tworzeniu klas Task i TaskRepo oraz dodawaniu adnotacji
 * do nich.
 */
public interface TaskRepository {
    List<Task> findAll();
    Page<Task> findAll(Pageable page);
    Optional<Task> findById(Integer id);
    boolean existsById(Integer id);
    boolean existsByDoneIsFalseAndGroup_Id(Integer id);
    Task save(Task entity);
    List<Task> findByDone(@Param("state") boolean done);
//    List<Task> findAllByGroupId(Integer id);  - to moja wersja,
//                                          ale oczywiście w kursie jest troche inaczej.

    List<Task> findAllByGroup_Id(Integer groupId);
}
