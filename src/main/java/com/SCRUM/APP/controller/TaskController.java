package com.SCRUM.APP.controller;

import com.SCRUM.APP.dtos.task.TaskDTOEntity; // DTO with full entities
import com.SCRUM.APP.dtos.task.TaskConverter;
import com.SCRUM.APP.dtos.task.TaskDTO;
import com.SCRUM.APP.model.Task;
import com.SCRUM.APP.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskConverter taskConverter;

    public TaskController(TaskService taskService, TaskConverter taskConverter) {
        this.taskService = taskService;
        this.taskConverter = taskConverter;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskConverter.dtoToTask(taskDTO);
        Task createdTask = taskService.createTask(task);
        return taskConverter.taskToDto(createdTask);
    }

    @GetMapping("/all")
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks.stream()
                .map(taskConverter::taskToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isPresent()) {
            TaskDTO taskDTO = taskConverter.taskToDto(taskOptional.get());
            return ResponseEntity.ok(taskDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskDTO>> getCompletedTasks() {
        List<Task> completedTasks = taskService.getCompletedTasks();
        List<TaskDTO> completedTaskDTOs = completedTasks.stream()
                .map(taskConverter::taskToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(completedTaskDTOs);
    }

    @GetMapping("/not_completed")
    public ResponseEntity<List<TaskDTO>> getNotCompletedTasks() {
        List<Task> notCompletedTasks = taskService.getNotCompletedTasks();
        List<TaskDTO> notCompletedTaskDTOs = notCompletedTasks.stream()
                .map(taskConverter::taskToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notCompletedTaskDTOs);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskConverter.dtoToTask(taskDTO);
        task.setId(id);
        Task updatedTask = taskService.updateTask(task);
        if (updatedTask != null) {
            TaskDTO updatedTaskDTO = taskConverter.taskToDto(updatedTask);
            return ResponseEntity.ok(updatedTaskDTO);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllCourses() {
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();

    }

    // Create task with full User and Project entities
    @PostMapping(value = "/create-entity", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTOEntity createTaskEntity(@RequestBody TaskDTOEntity taskDTOEntity) {
        Task task = taskConverter.dtoEntityToTask(taskDTOEntity); // Convert to Task
        Task createdTask = taskService.createTask(task); // Save Task
        return taskConverter.taskToDtoEntity(createdTask); // Return DTO with full entities
    }

    // Get all tasks with full User and Project entities
    @GetMapping("/entity")
    public List<TaskDTOEntity> getAllTasksEntity() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks.stream()
                .map(taskConverter::taskToDtoEntity) // Convert each task to TaskDTOEntity
                .collect(Collectors.toList());
    }

    // Get specific task by ID with full User and Project entities
    @GetMapping("/task-entity/{id}")
    public ResponseEntity<TaskDTOEntity> getTaskByIdEntity(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isPresent()) {
            TaskDTOEntity taskDTOEntity = taskConverter.taskToDtoEntity(taskOptional.get());
            return ResponseEntity.ok(taskDTOEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all completed tasks with full User and Project entities
    @GetMapping("/completed-entity")
    public ResponseEntity<List<TaskDTOEntity>> getCompletedTasksEntity() {
        List<Task> completedTasks = taskService.getCompletedTasks();
        List<TaskDTOEntity> completedTaskDTOEntities = completedTasks.stream()
                .map(taskConverter::taskToDtoEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(completedTaskDTOEntities);
    }

    // Get all not completed tasks with full User and Project entities
    @GetMapping("/not_completed-entity")
    public ResponseEntity<List<TaskDTOEntity>> getNotCompletedTasksEntity() {
        List<Task> notCompletedTasks = taskService.getNotCompletedTasks();
        List<TaskDTOEntity> notCompletedTaskDTOEntities = notCompletedTasks.stream()
                .map(taskConverter::taskToDtoEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notCompletedTaskDTOEntities);
    }

    // Update task with full User and Project entities
    @PutMapping("/task-entity/{id}")
    public ResponseEntity<TaskDTOEntity> updateTaskEntity(@PathVariable Long id, @RequestBody TaskDTOEntity taskDTOEntity) {
        Task task = taskConverter.dtoEntityToTask(taskDTOEntity);
        task.setId(id);
        Task updatedTask = taskService.updateTask(task);
        if (updatedTask != null) {
            TaskDTOEntity updatedTaskDTOEntity = taskConverter.taskToDtoEntity(updatedTask);
            return ResponseEntity.ok(updatedTaskDTOEntity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}


