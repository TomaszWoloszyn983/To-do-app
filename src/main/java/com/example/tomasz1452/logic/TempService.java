package com.example.tomasz1452.logic;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskGroup;
import com.example.tomasz1452.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * No i ok cała tak klasa została przemianowana na TaskGroupservice
 * i jej zawartość całkowiecie zmieniona.
 * Dlatego ja ją zachowałem i zamiast modyfokować ją utworzyłem
 * całkiem nową klasę TaskGroupService. Usunąłem adnotacje
 * Service i AutoWired aby nic z w Springu nie kolidowwało
 * i mam nadzieję że będzie to działało.
 */
//@Service
public class TempService {
//    @Autowired
    List<String> temp(TaskGroupRepository repository){
        /* Poniższy kod jest ponoć niezbyt dobry i wydajny.
           gdyż powoduje wysłanie n+1 zapytań, czy jakoś tak
           w każdym razie w klasie SqlTaskGroupRepository mamy
           lepsze rozwiazanie tego, za pomocą wysyłania zapytań
           za pomoca encji Hybernatowaych.
        */
        return repository.findAll().stream()
                .flatMap(taskGroup -> taskGroup.getTasks().stream())
                .map(Task::getDescription)
                .collect(Collectors.toList());
        /*
        Przy okazji przypomnimy sobie trochę mapowanie:
        Tworzymy stream wszystkich naszych grup zadań.
        Następnie dla każdej grupy tworzymy nowy stream który będzie
        pobierał każde zadanie z grupy i do każdego zadania będzie pobierał
        opis tego zadania.
        Na końcu rzutujemy to wszystko do formatu listy, abyśmy mogli zwrócić
        typ zgodny z typem zwracanym metody.
         */
    }
}
