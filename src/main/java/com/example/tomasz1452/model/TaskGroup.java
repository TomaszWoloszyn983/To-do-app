package com.example.tomasz1452.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 TaskGroup jest skopiowaną i zmotyfikowaną klasą Task.

 TaskGroup będzie definiowała grupy zadań/tasków. Tak by zarządzając
 tą klasą można było jednocześnie zarządzać jakąś grupą zadań.
 Dodojąc w klasie Task odnotację @ManyToOne przyporządkowujemy Task to
 do jakies grupy (na tym etapie jeszcze nie wiem co i jak).
 W tej klasie dodaliśmy za to adnotację @OneToMany, któa wydaje się
 logocznie wizać z ManyToOne w klasie Task.
 */

@Entity
@Table(name = "task_groups")
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Task groups description must not be empty")
    private String description;
    private boolean done;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;
        /*
        CascadeType.ALL oznacza że zaznaczając grupę zaznaczamy wszystkie
        elementy należąca do tej grupy. Jednocześnie modyfikując cokolwiek
        w tej grupie modyfikujemy (np. usuwamy) wszystkie elementy tej grupy.
         */
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;


    TaskGroup(){}


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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<Task> tasks) {
        this.tasks = tasks;
    }

    Project getProject() {
        return project;
    }

    void setProject(Project project) {
        this.project = project;
    }
}
