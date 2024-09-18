package com.SCRUM.APP.dtos.task;

import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.model.User;


public class TaskDTO {

        private Long id;
        private String name;
        private String description;
        private boolean completed;
        private Long projectId;
        private Long userId;

    public TaskDTO() {
    }

    public TaskDTO(Long id, String name, String description, boolean completed, Project project, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.projectId = project.getId();
        this.userId = user.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
