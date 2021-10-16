package com.example.tomasz1452.model.projection;

import com.example.tomasz1452.model.Project;
import com.example.tomasz1452.model.ProjectStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;

/**
 * Definiujemy tutaj tworzenie nowego projektu oraz
 * sosawanie do niego króków które trzeba wykonać aby
 * ukończyć projekt.
 */
public class ProjectWriteModel {
    @NotBlank(message = "Project description must not be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(List<ProjectStep> steps) {
        this.steps = steps;
    }

    public Project toProject(){
        var result = new Project();
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}
