package com.example.tomasz1452;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

/** Jeśli mamy kilka Beanów TaskRepository
     to będziemy odrużniać właściwy poprzez
     słowo/klucz integration.
     Dodanie np "!prod" odróznia profile przez wykluczenie.

     Przy okazji adnotacja Primary sprawia że ten Bean
     uzyskuje pierwszeństwo przy wywoływaniu profilu
     integration, czy jakoś tak.

    Dodaliśmy konfigurację dla !integration. Konfiguracja ta ma nam służyć
    do testów integeracyjnych. (czy nie powinna być właściwie odwrotnie?)
*/
@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource(){
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                "sa",
                "");
        result.setDriverClassName("org.h2.Driver");
        return result;

    }

    @Bean
    @Primary
    @Profile("integration")
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
                int key = tasks.size()+1;
                try{
                    var field = Task.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                tasks.put(key, entity);
                return tasks.get(key);
            }

            @Override
            public List<Task> findByDone(boolean done) {
                return null;
            }

            @Override
            public List<Task> findAllByGroup_Id(Integer groupId) {
                return List.of();
            }

//            @Override
//            public List<Task> findAllByGroupId(Integer id) {
//                return List.of();
//            }
        };
    }
}
