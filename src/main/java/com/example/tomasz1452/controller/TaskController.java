package com.example.tomasz1452.controller;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;


/**
 * Z tego co rozumiem to klasa ma za zadanie zarządzać
 * repozytorium zadań. W tym celu w klasie tworzone jest nowe
 * repozytorium (którym będziemy zarządzać).
 *
 * Ogólnie to ta klasa pośredniczy pomiędzy javą a rządaniami sql'owymi.
 *
 * Przede wszystkim @RestController mapuje naszą klasę TaskController
 * na JSona i pozwala nam modelować nasze rządania do serwera z wnetrza
 * obecnaej klasy. Dzięki temu mamy większą kontrole nad zawartością
 * tych żądań oraz zawartość żądań pozostaje prywatna (lub privatepackage'owa)
 *
 * Metody takie jak PostMapping, GetMapping rozszerzają (RequestMapping)
 * i służą z tego co kojarzę do nadpisywania domyślnych metod do wysyłania
 * requestów do serwera
 */


@RestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
         /*
         @RequestBody mapuje otrzymaną ze stony serwera zawartość JSonową na odpowiedni
         format javowy. Dzieki tamu ze Spring automatycznie rozpoznaję formaty
         samodzielnie dobiera odpoweidnie konwersje. W tym przypadku otrzymaną
         z adresu /tasks odpowedź mapuje na format Task.

         Adnotacje RequestMapping i GetMapping w tym przypadku działają
         tak samo. czyli wysyłają żądanie GET na adres http:cośtam/tasks
//     */
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
         /*
//        params = {"!sort", "!page","!size"} oznacza że metoda zostanie wywołana jeśli
            w żadaniu nie będą podane żadne z powyższych parametrów.
            W poniżeszej, przeciążonej metodzie nie ma tego warunku i Spring
            będzie szukał parametrów. W tym przypadku znajdzie on parametr page (pageble)
            To dośź skomplikowane, ale żeby przeciążyć metodę readAllTasks musieliśmy zmienić
            jej sygnaturę, czyli np dodac parametr. Spring chyba potrafi pobrac parametr
            z metody readAllTasks i podstawic go do sygnatury GetMapping
//         */
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
            /*
            Zostaje tutaj zwrócona strona zawierająca taski, a nie same taski.
            Dlatego trzeba pobrać zawartość strony za pomocą getContent()
             */
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
//        Optional<Task> result = repository.findById(id);
//        if(repository.existsById(id)){
//            return result.map(ResponseEntity::ok);
//        }else{
//            return ResponseEntity.notFound().build();
//        }
        /*
        To była moja próba rozwiązania tego zadania. Niestaty nie działa
        nie można odczytać tasków po id.
         */
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> {task.updateFrom(toUpdate);
                                    repository.save(task);
                                    });

        return ResponseEntity.noContent().build();
        /*
//         @RequestBody @Valid w parametrze oznacza że zadanie
//         toUpdate ma być wysłane oraz zvalidowane (czyli chyba
//         jakoś sprawdzone) Validator mamy skonfigurowany w klasie
//         TodoAppApplication
//
//         @PathVariable (zmienna ścieżki) jest pobierana z adresu url
//         /tasks/{id} i automatycznie konwertowana na zmienną typu int.

            Ogolnie działanie motody poalega na tym że otrzymujemy odpowiedź
            z serwera @PathVariable pobiera z adresu Id taska. Jesli task o takim
            Id nie istnieje zostaje zwrócone notFound. W przeciwnym razie nadajemy
            taskowi nowe ID (setId())
//         */
    }

    @Transactional
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
    //               Jeśłi zadanie o danym id istnieje w tabeli, to zmieniamy jego stan
    //               na przeciwny do tego który był.
        /*
        Po prostu ta metoda zmienia nam stan danego taska na przeciwny.
        Po wykonaniu tej metody jej efekt zostaje zakomitowany do hibernate'a
        pod warunkiem ż nie wyskoczył żaden błąd.
         */
        return ResponseEntity.noContent().build();
    }

}
