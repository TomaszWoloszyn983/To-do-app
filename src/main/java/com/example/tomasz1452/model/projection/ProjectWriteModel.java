package com.example.tomasz1452.model.projection;

import com.example.tomasz1452.model.Project;
import com.example.tomasz1452.model.ProjectStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Definiujemy tutaj tworzenie nowego projektu oraz
 * stosawanie do niego króków które trzeba wykonać aby
 * ukończyć projekt.
 *
 * Pisząc Html dodaliśmy tutaj konstruktor który przy tworzeniu
 * nowego obiektu rojectWriteModel od razu dodaje do projektu
 * jeden krok, tak aby przy otwieraniu html nie natrafiał on
 * na pustą listę tylko mogł się zatrzymać na pierwszym kroku(stepie)
 */
public class ProjectWriteModel {
    @NotBlank(message = "Project description must not be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps = new ArrayList<>();

    public ProjectWriteModel(){
        steps.add(new ProjectStep());
    }

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
