package com.example.tomasz1452.model.projection;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Model zapisywanie task'ów.
 * Klasa zawiera elementy potrzebne do utworzenia task'a,
 * ich gettery i settery oraz metodę zbierającą te elementy w jeden
 * task.
 */
public class GroupTaskWriteModel {
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Task toTask(final TaskGroup group){
        return new Task(description, deadline, group);
    }
}
