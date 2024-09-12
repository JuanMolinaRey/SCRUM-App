package com.SCRUM.APP.dtos.task;


import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.model.Task;
import com.SCRUM.APP.model.User;
import com.SCRUM.APP.service.ProjectService;
import com.SCRUM.APP.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskConverter {

    private final ModelMapper modelMapper;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskConverter(ModelMapper modelMapper, ProjectService projectService, UserService userService) {
        this.modelMapper = modelMapper;
        this.projectService = projectService;
        this.userService = userService;
    }

    public TaskDTO taskToDto(Task task) {
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        // Manually set projectId and userId
        taskDTO.setProjectId(task.getProject() != null ? task.getProject().getId() : null);
        taskDTO.setUserId(task.getUser() != null ? task.getUser().getId() : null);
        return taskDTO;
    }

    public Task dtoToTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);

        if (taskDTO.getProjectId() != null && projectService != null) {
            Optional<Project> optionalProject = projectService.getProjectById(taskDTO.getProjectId());
            optionalProject.ifPresent(task::setProject); // Set the project if present
        }

        if (taskDTO.getUserId() != null && userService != null) {
            Optional<User> optionalUser = userService.getUserById(taskDTO.getUserId());
            optionalUser.ifPresent(task::setUser); // Set the user if present
        }

        return task;
    }

}
