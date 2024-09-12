package com.SCRUM.APP.service;

import com.SCRUM.APP.model.Task;
import com.SCRUM.APP.repository.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Task task = new Task(1L, "Task 1", "Description 1", false, null, null);
        task.setId(1L);
        task.setName("New Task");

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);
        assertEquals(task.getName(), result.getName());
        assertEquals(task.getId(), result.getId());

        verify(taskRepository, times(1)).save(task);
    }


    @Test
    void testGetAllTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", false, null, null);
        Task task2 = new Task(2L, "Task 2", "Description 2", true, null, null);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getName());

        verify(taskRepository, times(1)).findAll();
    }


    @Test
    void testGetTaskById() {
        Task task = new Task(1L, "Task 1", "Description 1", false, null, null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getName());

        verify(taskRepository, times(1)).findById(1L);
    }


    @Test
    void testUpdateTask() {
        Task existingTask = new Task(1L, "Task 1", "Description 1", false, null, null);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", true, null, null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        Task result = taskService.updateTask(updatedTask);
        assertNotNull(result);
        assertEquals("Updated Task", result.getName());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void testDeleteTaskById() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTaskById(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);

    }

    @Test
    void testDeleteAllTasks() {
        doNothing().when(taskRepository).deleteAll();
        taskService.deleteAllTasks();

        verify(taskRepository, times(1)).deleteAll();
    }
}

