package com.example.tomasz1452.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Klasa definiuje podzadania konieczne do wykonania zadania głównego
 * Poza elementami jak Id oraz description, które zawiera także project
 * posiada jeszcze zmienną deadline, która określa ile dni zostaje nam
 * na wykonanie tego zadania.
 */
@Entity
@Table(name = "project_steps")
public class ProjectStep {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Project steps description must not be empty")
    private String description;
    private int daysToDeadline;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
