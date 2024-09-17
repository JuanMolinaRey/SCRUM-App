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
        taskDTO.setProjectId(task.getProject() != null ? task.getProject().getId() : null);
        taskDTO.setUserId(task.getUser() != null ? task.getUser().getId() : null);
        return taskDTO;
    }

    public Task dtoToTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);

        if (taskDTO.getProjectId() != null && projectService != null) {
            Project project = projectService.getProjectById(taskDTO.getProjectId());
            if (project != null) {
                task.setProject(project);
            }
        }

        if (taskDTO.getUserId() != null && userService != null) {
            Optional<User> optionalUser = userService.getUserById(taskDTO.getUserId());
            optionalUser.ifPresent(task::setUser);
        }

        return task;
    }

    public TaskDTOEntity taskToDtoEntity(Task task) {
        TaskDTOEntity taskDTOEntity = modelMapper.map(task, TaskDTOEntity.class);

        if (task.getProject() != null) {
            taskToDtoEntity(task).setProject(task.getProject());
        }
        if (task.getUser() != null) {
            taskToDtoEntity(task).setUser(task.getUser());
        }
        return taskDTOEntity;
    }
    public Task dtoEntityToTask(TaskDTOEntity taskDTOEntity) {
        Task task = modelMapper.map(taskDTOEntity, Task.class);

        if (taskDTOEntity.getProject() != null) {
            task.setProject(taskDTOEntity.getProject());
        }
        if (taskDTOEntity.getUser() != null) {
            task.setUser(taskDTOEntity.getUser());
        }
        return task;
    }



}
