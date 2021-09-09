package com.example.tomasz1452.model;

import com.example.tomasz1452.TaskConfigurationProperties;
import com.example.tomasz1452.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ProjectServiceTest {

    /**
     * Testujemy tutaj metodę createGroup z ProjectService.
     * Test powinien zawierać trzy podpunkty given, when, then/ W pierwszym
     * przydotowujemy dane do testu, when to wykoananie testu, then to obserwacja
     * skutków.
     */
    @Test
    @DisplayName("Should throw IllegalStateException when configured to allow just 1 group and the other undone group exist")
    void createGroup_noMultipleGroupConfig_And_undoneGroupExists_throwsIllegalStateException() {
        // given
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
//        assertTrue(mockGroupRepository.existsByDoneIsFalseAndProject_Id(1));

        TaskConfigurationProperties mockConfig = configurationReturning(false);

        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);

//        when + then
//        assertThatIllegalStateException().isThrownBy(() -> toTest.createGroup(LocalDateTime.now(), 0));
                /*
                Tutaj testuejmy czy podany w parametrze kod wyrzuca IllegalStateException
                 */

        //when
//        toTest.createGroup(LocalDateTime.now(), 0);

        // then

//        when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//        then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
            /*
            Testujemy wystąpienie wyrażenia "one undone group".
            We wcześniejszym zacytatowanym kodzie przetestowaliśmy wystąpienie wyjątku IllegalStateException
            jednak słabością tamtego rozwiązania było to że ten bład mógł poajwić się także z innych
            nieprzewidzianych przez nas powodów a my nie byliśmy w stanie tego określić za pomocą tego
            testu.
            Obecna tutaj wersja testuje wystąpienie komunikatu który pojawia się wraz z wyrzuceniem tego
            wyjątku, sprawia to że nasz test jest tutaj rzeczywiście testem jednostkowym.
             */
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configuration ok and no projects for a given id")
    void createGroup_configurationOk_And_noProjects_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        TaskConfigurationProperties mockConfig = configurationReturning(true);

        var toTest = new ProjectService(mockRepository, null, mockConfig);

        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//        then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id not found");
                    /*
                    W takich przypadkach trzeba bardzo uważać na literówki i małe
                    i duże litery.
                    Np. tutaj początkowo zapisałem id z małej litery podczas gdy
                    w testowanej metodzie w ProjectService id było zapisane z dużej
                    Powodowało to oczywiście że test nie przechodził.
                     */
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configured to allow just one group and no groups and projects for a given id")
    void createGroup_noMultiGroupsConfig_And_noUndoneGroupExists_noProjects_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);

        var toTest = new ProjectService(mockRepository, null, mockConfig);

        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//        then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id not found");
    }

    @Test
    @DisplayName("Should create a new group from project")
    void createGroup_ConfigurationOk_existingProject_createAndSavesGroup(){

        /*
            Ten test ma za zadanie sprawdzić czy utworzona przez nas grupa
            zadań zostanie zapisana w repozytorium.
            W tym celu tworzymy mową przykładową TaskGroupRepository o nazwie
            inMemoryGroupRepository która służy tylko takiemu celowi, aby
            przeprowadzić symulację całego procesu zapisywanie na potrzeby
            testu.
            Dodaliśmy na przykład też zmienną today która jest potrzebna do
            wywołania metody createGroup itp.

             */

//        given
        var today = LocalDate.now().atStartOfDay();
//        and
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(
                         projectWith("bar", Set.of(-1, -2))
                ));
//        and
        inMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        int countBeforeCall = inMemoryGroupRepo.count();
//        and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
//        System under test
        var toTest = new ProjectService(mockRepository, inMemoryGroupRepo, mockConfig);

//        when
        GroupReadModel result = toTest.createGroup(today, 1);

//        then
//        assertThat(result)
        assertThat(result.getDescription()).isEqualTo("bar");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(result.getTasks())
//                .isEqualTo(today.minusDays(1));
                .allMatch(task -> task.getDescription().equals("foo"));
        assertThat(countBeforeCall + 1)
                .isNotEqualTo(inMemoryGroupRepo.count());
    }

    private Project projectWith(String projectDescription, Set<Integer> daysToDeadline){
        var result = mock(Project.class);
        when(result.getDescription()).thenReturn(projectDescription);
        when(result.getSteps()).thenReturn(
                daysToDeadline.stream()
                .map(days -> {
                    var step = mock(ProjectStep.class);
                    when(step.getDescription()).thenReturn("foo");
                    when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet())
        );
        return result;
    }

    private TaskGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private TaskConfigurationProperties configurationReturning(final boolean result) {

        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);

        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private inMemoryGroupRepository inMemoryGroupRepository(){
        return new inMemoryGroupRepository();
    }

    private static class inMemoryGroupRepository implements TaskGroupRepository{
        private int index = 0;
        private Map<Integer, TaskGroup> map = new HashMap<>();

        public int count(){
            return map.values().size();
        }

        @Override
        public List<TaskGroup> findAll() {
            return new ArrayList<>(map.values());
            //Zwaraca rozmiar kolekcji
        }

        @Override
        public Optional<TaskGroup> findById(Integer Id) {
            return Optional.ofNullable(map.get(Id));
        }

        @Override
        public TaskGroup save(TaskGroup entity) {
            if(entity.getId() == 0){
                try{
                    TaskGroup.class.getDeclaredField("id").set(entity, ++index);
                }catch (NoSuchFieldException | IllegalAccessException e){
                    throw new RuntimeException();
                }
            }
            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(final Integer projectId) {
            return map.values().stream()
                    .filter(group -> !group.isDone())
                    .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);
        }
    }
}