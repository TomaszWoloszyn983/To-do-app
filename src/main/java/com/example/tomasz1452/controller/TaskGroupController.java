package com.example.tomasz1452.controller;

import com.example.tomasz1452.logic.TaskGroupService;
import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskGroup;
import com.example.tomasz1452.model.TaskGroupRepository;
import com.example.tomasz1452.model.TaskRepository;
import com.example.tomasz1452.model.projection.GroupReadModel;
import com.example.tomasz1452.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


/**
 * W tej klasie będziemy zarządzać repozytorium TaskGroup
 * będziemy definiować zapytania do serwera zawarte w repo
 * TaskGroup (na wzrór tego co mamy w TaskController).
 *
 * Zapytanie które musimy wysłać do serwera:
 *      createGroup(GroupWriteModel source)
 *      readAll()
 *      toggleGroup(int groupId)
 *
 * Do tego w TaskRepository musimy dodać nową metodę zwracającą
 * Listę wszystkich zadań znajdujących się w jednej grupie oraz oczywiście
 * wykonać do tej metody rządanie do serwera.
 *
 * Nie odczytuje mi w ogole grupTasków. Pomimo to dodawanie grup działa, zmiana stanu grupy na
 * true i false też działa.
 *
 *                  Uwaga
 *  Moja metoda toggle group działa na moim repozytorium grup. Podczas gdy
 *  metoda z kursu zwraca bładd 500.
 */
@RestController
@RequestMapping("/task_groups")
class TaskGroupController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskGroupRepository repository;
    private final TaskGroupService service;
    private final TaskRepository taskRepository;

    TaskGroupController(TaskGroupRepository repository, TaskGroupService service, TaskRepository taskRepository) {
        this.repository = repository;
        this.service = service;
        this.taskRepository = taskRepository;
    }

//    TaskGroupController(TaskGroupRepository repository, TaskGroupService service, TaskRepository taskRepository) {
//        this(repository, service);
//        this.taskRepository = taskRepository;
//    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate){
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(service.createGroup(toCreate));
    }

    @GetMapping()
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.warn("Displaying all Taskgroups!");
        return ResponseEntity.ok(service.readAll());
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    /**
     *  Należy utworzyć na poziomie klasy obiekt TaskRepository
     *  Pobrać grupę o podanym w parametrze id
     *  Odczytac taski z tej grupy.
     *  Zrzutowac odczytane taski do Listy albo zbioru.
     *
     *  Próbowałem utworzyć tutaj nowe TaskRepository, ale okazało się
     *  że muszę zaimplementować wszystkie motody z TaskRepo, a potrzebujemy tylko
     *  findAllByGroupId
     *
     *  Trzeba dodać metode która będzie dodawała nam zadania do grup o jakimś podanym Id
     *  tylko tak będę mógł sprawdzić czy readTasksInGroup działa.
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<Task>> readTasksFromGroup(@PathVariable int id){

//        TaskGroup result = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(
//                "TaskGroup with given Id not found"));      // W razie gdy grupa o podanym Id nie istnieje.

        return ResponseEntity.ok(
                taskRepository.findAllByGroup_Id(id));
    }

//          Rozwiazanie z kursu. Ja się na razie posługuję swoją wersją
//    @Transactional
//    @PatchMapping("/{id}")
//    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
//        if (!repository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        repository.findById(id)
//                .ifPresent(task -> task.setDone(!task.isDone()));
//
//        return ResponseEntity.noContent().build();
//    }

    /*
    Poniżej mamy ExceptionHandlery do błędów które sa automatycznie wywołaywane
    przez metodę service.toggleGroup(id)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
