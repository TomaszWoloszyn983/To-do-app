package com.example.tomasz1452.model;

import com.example.tomasz1452.TaskConfigurationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
//        assertTrue(mockGroupRepository.existsByDoneIsFalseAndProject_Id(1));

        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);

        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);

        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);

        //when
        toTest.createGroup(LocalDateTime.now(), 0);

        // then
    }
}