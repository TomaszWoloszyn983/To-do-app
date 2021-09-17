package com.example.tomasz1452.logic;

import com.example.tomasz1452.TaskConfigurationProperties;
import com.example.tomasz1452.model.ProjectRepository;
import com.example.tomasz1452.model.ProjectService;
import com.example.tomasz1452.model.TaskGroupRepository;
import com.example.tomasz1452.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ta klasa póki co służy tylko jako prykład zastosawonia Beana.
 * Polega to na tym że zastosowanie Bean dodaje do Springa zupełnie
 * wyjętą z kontekstu klasę. Spring sa będzie potrafił taką klasę użyć
 * do tego będzie potrafił odnaleść i dociągnąć sobie też klasę
 * ProjectService pomimo że z PS usunęliśmy wszystkie Springowe
 * adnotacje.
 */
@Configuration
class LogicConfiguration {
    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config){
        return new ProjectService(repository, taskGroupRepository, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository repository,
            final TaskRepository taskRepository){
        return new TaskGroupService(repository, taskRepository);
    }

}
