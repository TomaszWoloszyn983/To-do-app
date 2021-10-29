package com.example.tomasz1452.controller;

import com.example.tomasz1452.logic.TaskGroupService;
import com.example.tomasz1452.model.*;
import com.example.tomasz1452.model.projection.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;


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
@Controller
@IllegalExceptionProcessing
@RequestMapping("/groups")
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

    /*
    produces, consumes zonacz chyba jakiego typu dane pobiera metoda oraz jakiego typu
    dane zwraca. W ponizszym przypadku jest to JSON.
     */
    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate){
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(service.createGroup(toCreate));
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.warn("Displaying all Taskgroups!");
        return ResponseEntity.ok(service.readAll());
    }

    @ResponseBody
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
    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    /**
     * Zadanie samodzielne.
     * Próbuję tutaj utworzyć metodę dodającą taski do taksgrupy.
     * Robię to w oparciu o dodawanie stepów do projektów w klasie ProjectController
     *
     * A więc tak jakby wysyłamy żadanie Post z parametrem addTask
     *
     * @return

    @PostMapping(produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String addTaskToGroup(@ModelAttribute("group") GroupWriteModel current){
//        current.getSteps().add(new ProjectStep());
        current.getTasks().add(new GroupTaskWriteModel());
        return "task_groups";
    }
     */

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model){
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @PostMapping(params = "addTask", produces = MediaType.TEXT_HTML_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel current){
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model){

        if(bindingResult.hasErrors()){
            return "groups";
        }

        service.createGroup(current);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Dodano grupę!");
        return "groups";
    }

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups(){
        return service.readAll();
    }

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
