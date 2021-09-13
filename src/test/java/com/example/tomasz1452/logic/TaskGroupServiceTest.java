package com.example.tomasz1452.logic;

import com.example.tomasz1452.model.TaskGroup;
import com.example.tomasz1452.model.TaskGroupRepository;
import com.example.tomasz1452.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

        /*
        W tym przykładzie testujemy reakcję programu na wykrycie w repo zadań zadania
        niewykonanego.
        W tym celu tworzymy jakieś przykładowe repo zadań (ponieważ będziemy używali
        go w dwóch przykładach wynieśliiśmy je do zmiennej taskRepositoryReturning.
        Tworzymy przkłądowy obiekt klasy TaskGroupService do którego podstawiamy
        taskRepositoryReturning.
        Dalej wygląda z utworzonego obiektu toTest wywolujemy meetodę toggleGroup
        i jeśli ta znjdzie w podanym Id błąd to ten błąd zostanei zapisany
        w zmiennej exception.
        i wtedy jeśli błąd jest instancję IllegalStateException to wysyłamy
        wiadomość "undone tasks"

        I widze że w następnym przykładzie wygląda to podobnie
         */
    @Test
    @DisplayName("should throw when undone tasks")
    void toggleGroup_undoneTasks_throwsIllegalStateException(){
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        // system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);

        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException(){
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id not found");
                    /*
                    I tutaj po raz kolejny wyszło to że zapisałem
                    w klasie Id dużą literą (w kursie było to "id")
                    Teraz by testy przechodzoły muszę pamiętać o tym
                    aby wpisywać Id dużą literą.
                     */
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup_worksAsExpected() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        var group = new TaskGroup();
        var beforeToggle = group.isDone();
        // and
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
        // system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        // when
        toTest.toggleGroup(0);

        // then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(final boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }

}