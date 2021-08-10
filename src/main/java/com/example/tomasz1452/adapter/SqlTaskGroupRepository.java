package com.example.tomasz1452.adapter;

import com.example.tomasz1452.model.TaskGroup;
import com.example.tomasz1452.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlTaskGroupRepository extends TaskGroupRepository,
        JpaRepository<TaskGroup, Integer> {
    @Override
    @Query("from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();

    /*
    Stworzyliśmy tutaj zapytanie HQL'owe (Hybernate Query Language)
    Dla kazdej grupy zadań g podajemy wszystkie zadania z tej grupy.
    TaskGroup odnosi się do klasy TaskGroup.
     */

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}