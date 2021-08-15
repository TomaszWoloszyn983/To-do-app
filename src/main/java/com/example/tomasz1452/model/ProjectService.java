package com.example.tomasz1452.model;

import com.example.tomasz1452.TaskConfigurationProperties;
import com.example.tomasz1452.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository repository,
                          TaskGroupRepository taskGroupRepository,
                          TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }

    public Project save(final Project toSave){
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasks()
                && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        TaskGroup result = repository.findById(projectId).map(project -> {
            var targetGroup = new TaskGroup();
            targetGroup.setDescription((project.getDescription()));
            targetGroup.setTasks(
                    project.getSteps().stream()
                    .map(projectStep -> new Task(
                            projectStep.getDescription(),
                            deadline.plusDays(projectStep.getDaysToDeadline()))
                    )
                    .collect(Collectors.toSet())
            );
            return targetGroup;
        }).orElseThrow(() -> new IllegalArgumentException("Project group with given Id not found"));
        return new GroupReadModel(result);
    }


    /*
        No i to było zadanie samodzielne, którego aż wstyd że nie mogłem ugryść

        readAll() wywołuje tylko metodę findAll() z ProjectRepository. W tym celu
        tylko tworzymy instancję ProjectRepository.
        Podobnie z save().
     */
}
