package com.example.tomasz1452.model;

import com.example.tomasz1452.TaskConfigurationProperties;
import com.example.tomasz1452.logic.TaskGroupService;
import com.example.tomasz1452.model.projection.GroupReadModel;
import com.example.tomasz1452.model.projection.GroupTaskWriteModel;
import com.example.tomasz1452.model.projection.GroupWriteModel;
import com.example.tomasz1452.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    public ProjectService(ProjectRepository repository,
                          TaskGroupRepository taskGroupRepository,
                          TaskConfigurationProperties config, TaskGroupService service) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = service;
        this.config = config;

    }

    public List<Project> readAll(){
        return repository.findAll();
    }

    public Project save(final ProjectWriteModel toSave){
        return repository.save(toSave.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasks()
                && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return repository.findById(projectId).map(project -> {
            var targetGroup = new GroupWriteModel();
            targetGroup.setDescription(project.getDescription());
            targetGroup.setTasks(
                project.getSteps().stream()
                .map(projectStep -> {
                        var task = new GroupTaskWriteModel();
                        task.setDescription(projectStep.getDescription());
                        task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                        return task;
                    }
                ).collect(Collectors.toList())
            );
            return taskGroupService.createGroup(targetGroup, project);
        }).orElseThrow(() -> new IllegalArgumentException("Project group with given Id not found"));

    }


    /*
        No i to było zadanie samodzielne, którego aż wstyd że nie mogłem ugryść

        readAll() wywołuje tylko metodę findAll() z ProjectRepository. W tym celu
        tylko tworzymy instancję ProjectRepository.
        Podobnie z save().
     */
}
