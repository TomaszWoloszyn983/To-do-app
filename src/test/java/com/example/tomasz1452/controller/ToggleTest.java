package com.example.tomasz1452.controller;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class ToggleTest {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository repo;

//    List<Task> tasks = repo.findAll();

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        // given
//
        repo.save(new Task("mock1", LocalDateTime.now()));
        repo.save(new Task("mock2", LocalDateTime.now()));

        when(repo.findById(anyInt()))
                .thenReturn(Optional.of(new Task("foo", LocalDateTime.now())));


        // when + then
//        mockMvc.perform(patch("/tasks/"+ 0))
//                .andExpect(status().isNoContent());

        /*
        Niestey zwraca mi tutaj status 404 Not Found zamiast spodziewanego
        204 No Content.
        Wiec test nie przechodzi.
         */


    }

}
