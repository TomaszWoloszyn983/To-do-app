package com.example.tomasz1452.controller;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Tworzymy tutaj test integracyjny
 * Testujemy odpalenie SpringBoota chyba.
 *
 * Dociągnęliśmy specjalną klasę do testowania MockMvc
 * oraz mockowy TaskRepository.
 *
 * Dalej tworzymy metodę testującą.
 * Zapisujemy w mockowej bzie dancyh jakiegoś taska, pobieramy jego id
 * i następnie testujemy pobranie tego taska oczekując sukcesu.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() throws Exception{
        // given
        int id = repo.save(new Task("foo", LocalDateTime.now())).getId();

        // when + then
        mockMvc.perform(get("/tasks/"+ id))
                .andExpect(status().is2xxSuccessful());
    }
}
