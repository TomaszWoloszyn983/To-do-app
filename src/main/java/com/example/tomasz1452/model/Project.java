package com.example.tomasz1452.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Definiujemy tutaj Project czyli grupę zadań na którą będą składały się
 * pomniejsze podzadania (ProjectStep) konieczne do wykonania całego zadania z projektu.
 *
 * Aby utwerzyć tę klasę musieliśmy dodać nowe tabele do naszej Hybernate'owej bazy
 * danych. Dokonałiśmy tego w pliku mirgacji danych V6__
 * Na potrzeby tej klasy dodaliśmy tylko dwie nowe tabele które musi posiadać
 * klasa Project, czyli id i description.
 * Migracja zawiara też inne tabele, które będą słóżyły klasie ProjectStep.
 */
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Projects description must not be empty")
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> groups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> steps;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    Set<TaskGroup> getGroup() {
        return groups;
    }

    void setGroup(Set<TaskGroup> group) {
        this.groups = group;
    }

    Set<TaskGroup> getGroups() {
        return groups;
    }

    void setGroups(Set<TaskGroup> groups) {
        this.groups = groups;
    }

    Set<ProjectStep> getSteps() {
        return steps;
    }

    void setSteps(Set<ProjectStep> steps) {
        this.steps = steps;
    }
}
