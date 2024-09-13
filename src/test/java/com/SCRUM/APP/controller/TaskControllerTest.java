package com.SCRUM.APP.controller;

import com.SCRUM.APP.dtos.task.*;
import com.SCRUM.APP.model.Task;
import com.SCRUM.APP.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SCRUM.APP.dtos.task.TaskConverter;
import com.SCRUM.APP.dtos.task.TaskDTO;
import com.SCRUM.APP.model.Task;
import com.SCRUM.APP.service.TaskService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskConverter taskConverter;
    @InjectMocks
    private TaskController taskController;

    private MockMvc mockController;

    private TaskDTO taskDTO1;
    private TaskDTO taskDTO2;
    private List<TaskDTO> taskList = new ArrayList<>();
    private TaskDTOEntity taskDTOEntity1;
    private TaskDTOEntity taskDTOEntity2;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        mockController = MockMvcBuilders.standaloneSetup(taskController).build();

        taskDTO1 = new TaskDTO(1L, "Task 1", "First task", false, null, null);
        taskDTO2 = new TaskDTO(2L, "Task 2", "Second task", true, null, null);
        task1 = new Task(1L, "Task 1", "First task", false, null, null);
        task2 = new Task(2L, "Task 2", "Second task", true, null, null);
        taskList = List.of(taskDTO1, taskDTO2);
        taskDTOEntity1 = new TaskDTOEntity(1L, "Task 1", "First task", false, null, null);
        taskDTOEntity2 = new TaskDTOEntity(2L, "Task 2", "Second task", true, null, null);

        when(taskConverter.dtoToTask(any(TaskDTO.class))).thenReturn(task1);
        when(taskService.createTask(any(Task.class))).thenReturn(task1);
        when(taskConverter.taskToDto(any(Task.class))).thenReturn(taskDTO1);
        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));
        when(taskService.getTaskById(anyLong())).thenReturn(Optional.of(task1));
        when(taskService.updateTask(any(Task.class))).thenReturn(task1);
        doNothing().when(taskService).deleteTaskById(anyLong());
        when(taskService.getCompletedTasks()).thenReturn(List.of(task2));
        when(taskService.getNotCompletedTasks()).thenReturn(List.of(task1));

        when(taskConverter.taskToDto(any(Task.class))).thenReturn(taskDTO1).thenReturn(taskDTO2);
        when(taskConverter.dtoToTask(any(TaskDTO.class))).thenReturn(task1);
    }


    @Test
    void createTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task1);

        String taskJson = "{"
                + "\"id\": 1,"
                + "\"name\": \"Task 1\","
                + "\"description\": \"First task\","
                + "\"completed\": false"
                + "}";

        mockController
                .perform(post("/api/v1/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{"
                        + "\"id\": 1,"
                        + "\"name\": \"Task 1\","
                        + "\"description\": \"First task\","
                        + "\"completed\": false"
                        + "}"));
    }
    @Test
    void getAllTasks() throws Exception {
        mockController.perform(get("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(taskList), responseBody, false);
                });
    }

    @Test
    void getTaskById() throws Exception {
        mockController.perform(get("/api/v1/tasks/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(taskDTO1), responseBody, false);
                });
    }
    @Test
    void getCompletedTasks() throws Exception {
        when(taskService.getCompletedTasks()).thenReturn(List.of(task2));
        when(taskConverter.taskToDto(any(Task.class))).thenReturn(taskDTO2);

        mockController.perform(get("/api/v1/tasks/completed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(List.of(taskDTO2)), responseBody, false);
                });

        verify(taskService).getCompletedTasks();
    }

    @Test
    void getNotCompletedTasks() throws Exception {
        when(taskService.getNotCompletedTasks()).thenReturn(List.of(task1));
        when(taskConverter.taskToDto(any(Task.class))).thenReturn(taskDTO1);

        mockController.perform(get("/api/v1/tasks/not_completed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(List.of(taskDTO1)), responseBody, false);
                });

        verify(taskService).getNotCompletedTasks();
    }


    @Test
    void updateTask() throws Exception {
        String updatedTaskJson = "{"
                + "\"id\": 2,"
                + "\"name\": \"Updated Task 2\","
                + "\"description\": \"Updated description\","
                + "\"completed\": true"
                + "}";

        when(taskConverter.dtoToTask(any(TaskDTO.class))).thenReturn(task2);

        mockController.perform(put("/api/v1/tasks/task/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTaskJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    try {
                        JSONAssert.assertEquals(updatedTaskJson, responseBody, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
    }
    @Test
    void deleteTaskById() throws Exception {
        doNothing().when(taskService).deleteTaskById(anyLong());

        mockController.perform(delete("/api/v1/tasks/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTaskById(1L);
    }

    @Test
    void deleteAllTasks() throws Exception {
        doNothing().when(taskService).deleteAllTasks();

        mockController.perform(delete("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService).deleteAllTasks();
    }
    @Test
    void createTaskEntity() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task1);
        when(taskConverter.taskToDtoEntity(any(Task.class))).thenReturn(taskDTOEntity1);

        ObjectMapper objectMapper = new ObjectMapper();
        String taskJson = objectMapper.writeValueAsString(taskDTOEntity1);

        mockController.perform(post("/api/v1/tasks/create-entity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{"
                        + "\"id\": 1,"
                        + "\"name\": \"Task 1\","
                        + "\"description\": \"First task\","
                        + "\"completed\": false,"
                        + "\"project\": {\"id\": 1},"
                        + "\"user\": {\"id\": 1}"
                        + "}"));
    }
    //I am getting a serialization problem with tests because user and project are entities in dto.
    /*@Test
    void createTask2() throws Exception {
        // Mock service and converter responses
        when(taskService.createTask(any(Task.class))).thenReturn(task1);
        when(taskConverter.taskToDtoEntity(any(Task.class))).thenReturn(taskDTOEntity1);

        // Create the JSON payload for the test
        String taskJson = "{"
                + "\"id\": 1,"
                + "\"name\": \"Task 1\","
                + "\"description\": \"First task\","
                + "\"completed\": false"
                + "}";

        // Perform the POST request
        ResultActions resultActions = mockController.perform(post("/api/v1/tasks/create-entity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()); // Print the request and response for debugging

        // Print the actual response body for debugging
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        // Validate the response JSON
        try {
            JSONAssert.assertEquals("{"
                    + "\"id\": 1,"
                    + "\"name\": \"Task 1\","
                    + "\"description\": \"First task\","
                    + "\"completed\": false"
                    + "}", responseBody, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}


