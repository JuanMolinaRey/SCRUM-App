package com.SCRUM.APP.dtos.task;

import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.model.User;


public class TaskDTO {

        private int id;
        private String name;
        private String description;
        private boolean completed;
        private Project project;
        private User user;
    public TaskDTO() {
    }
    public TaskDTO(int id, String name, String description, boolean completed, Project project, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.project = project;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
