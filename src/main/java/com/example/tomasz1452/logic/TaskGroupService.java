package com.example.tomasz1452.logic;

import com.example.tomasz1452.model.TaskGroup;
import com.example.tomasz1452.model.TaskGroupRepository;
import com.example.tomasz1452.model.TaskRepository;
import com.example.tomasz1452.model.projection.GroupReadModel;
import com.example.tomasz1452.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;


public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository){
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }


    /**
     * Tutaj chodzi o to aby po wykonaniu wszystkich zadań w grupie zadań, cała grupa była
     * zazanaczana na odchaczoną/wykonaną.
     *
     * Najpierw przeszukujemy repozytorium tasków w poszukiwaniu niewykonanych tasków i jeśli taki
     * znajdziemy wyrzucamy bład.
     *
     * Dalej szukamy grupy tasków o podanym Id jeśli nie ma takiej to Błąd
     *
     * Na końcu jeśli grupa nie jest jeszcze odchaczona to odchaczamy ją.
     *
     * @param groupId
     */
    public void toggleGroup(int groupId){
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group contains undone tasks. Please done all the tasks first");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException(
                "TaskGroup with given Id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
