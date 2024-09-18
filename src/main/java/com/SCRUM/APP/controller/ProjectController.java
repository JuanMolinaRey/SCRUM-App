package com.SCRUM.APP.controller;


import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.service.ProjectService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/projects")
public class ProjectController {


    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "/list")
    public ArrayList<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping(path = "/list/{id}")
    public Project getProjectById(@PathVariable("id") Long id){
        return projectService.getProjectById(id);
    }

    @GetMapping("/list/completed/{completed}")
    public ResponseEntity<List<Project>> getProjectsByCompletedStatus(@PathVariable boolean completed) {
        List<Project> projects = projectService.getProjectsByCompletedStatus(completed);
        if (projects.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(projects);
        }
    }

    @PostMapping(path ="/create")
    public Project createProject(@RequestBody Project newProject){
        return projectService.createProject(newProject);
    }

    @PutMapping(path ="/update/{id}")
    public void updateProject(@RequestBody Project project, @PathVariable("id") Long id) {
        project.setId(id);
        projectService.updateProject(project);
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteProject(@PathVariable("id")Long id){
        return projectService.deleteProject(id);
    }
}
