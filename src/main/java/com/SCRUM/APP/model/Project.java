package com.SCRUM.APP.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Completed")
    private boolean completed;

    @OneToMany(mappedBy = "project")
    @JsonBackReference
    private List<Task> tasks;

    @ManyToMany(mappedBy = "projectsList")
    @JsonBackReference
    private List<User> usersList;

    public Project(Long id, String name, String description, boolean completed, List<Task> tasks, List<User> usersList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.tasks = tasks;
        this.usersList = usersList;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}

