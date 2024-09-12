package com.SCRUM.APP.service;

import com.SCRUM.APP.model.Task;
import com.SCRUM.APP.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }
    public List<Task> getAllTasks(){
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all users");
        }
    }
    public Optional<Task> getTaskById (Long id){
        try {
            return taskRepository.findById(id);

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user by id");
        }
    }
    public Task updateTask (Task updatedTask){
        Optional<Task> existingTask = taskRepository.findById(updatedTask.getId());
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);

        }return null;
    }
    public void deleteTaskById(Long id){
        taskRepository.deleteById(id);
    }
    public void deleteAllTasks(){
        taskRepository.deleteAll();
    }
}
