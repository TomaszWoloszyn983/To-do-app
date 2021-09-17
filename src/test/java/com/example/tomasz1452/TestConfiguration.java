package com.example.tomasz1452;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class TestConfiguration {
    @Bean
    @Profile({"integration"}) // Jeśli mamy kilka Beanów TaskRepository
                            // to będziemy odrużniać właściwy poprzez
                            // słowo/klucz integration.
                            // Dodanie np "!prod" odróznia profile przez wykluczenie.
    TaskRepository testRepo(){
        return new TaskRepository() {
            private Map<Integer, Task> tasks = new HashMap<>();
            @Override
            public List<Task> findAll() {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public Page<Task> findAll(Pageable page) {
                return null;
            }

            @Override
            public Optional<Task> findById(Integer id) {
                return Optional.ofNullable(tasks.get(id));
            }

            @Override
            public boolean existsById(Integer id) {
                return tasks.containsKey(id);
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(Integer id) {
                return false;
            }

            @Override
            public Task save(Task entity) {
                return tasks.put(tasks.size() + 1, entity);
            }

            @Override
            public List<Task> findByDone(boolean done) {
                return null;
            }
        };
    }
}
