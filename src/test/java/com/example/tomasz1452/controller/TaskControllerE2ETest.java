package com.example.tomasz1452.controller;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*
        Testy integracyjne
 */

/**
 * Ten test będzie testował połaczenie z serwerem.
 * W tym celu klasa WebEnviroment tworzy port o jakimś losowym numerze.
 * Ten numer będzie przechowywany prze zmienną port następnie klasa
 * TestRestTemplate będzie "odpytuje" serwer z różnych usłóg wysyłąjąc
 * rządanie get.
 *
 * Ogólnie w skrócie tworzymy nowe mockowe połączenie z serwerem.
 * Tworzymy mockawą bazę danych i zapisujemy do niej dwa zadania
 * Sprawdzamy teraz czy nasza baza zawiera dwa zadania.
 *
 * Konieczne było tutaj dodanie adnotacji integeration aby test pracował
 * na jednorazowej mockowej bazie a nie na bazie roboczej (kilka razy tak
 * się stało). W TestConfiguration konieczne było dodanie adnotacji Primary.
 *
 * Usunęliśmy adnotację ActiveProfile. Dlatego że w TastConfiguration dodaliśmy
 * jakąś konfigurację dla !integration
 *
 * Dodaliśmy zmienną initial, która przechowuje początkowy rozmiar naszej bazy danych.
 * Teraz aby sprawdzic wynik naszwego testu do iniatial dodaję ilość mockowych zadań.
 */
//@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testTemplate;

    @Autowired
    TaskRepository repo;

    @Test
    void http_returnsAllTasks(){
        // given
        int initial = repo.findAll().size();
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));

        // when
        Task[] result = testTemplate.getForObject("http://localhost:"+port+"/tasks",
                Task[].class);

        // then
        assertThat(result).hasSize(initial+2);
    }

}