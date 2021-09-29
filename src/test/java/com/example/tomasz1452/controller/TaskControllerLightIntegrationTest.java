package com.example.tomasz1452.controller;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


/**
 * Tutaj mamy chyba prawie wszystko to samo co w tescie TaskControllerIntegrationTest
 * z drobnymi zmianami.
 */
@WebMvcTest(TaskController.class)
@ActiveProfiles("integration")
class TaskControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        // given
        String description = "foo";
        when(repo.findById(anyInt()))
                .thenReturn(Optional.of(new Task("foo", LocalDateTime.now())));
//            int id = repo.save(new Task("foo", LocalDateTime.now())).getId();

        // when + then
        mockMvc.perform(get("/tasks/123"))
                .andDo(print())
                .andExpect(content().string(containsString(description)));
    }

    /*
    Moze w oparciu o powyższy test będę mógł wykonać zadanie samodzielne
    czyli dodanie testu który będzie sprawdzał działanie chyba metody
    patch która będzie zmieniała stan naszego zadania. Czy jest ono wykonaane
    czy nie.
     */

}
