package com.SCRUM.APP.service;

import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.repository.IProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final IProjectRepository iProjectRepository;

    public ProjectService(IProjectRepository iProjectRepository) {
        this.iProjectRepository = iProjectRepository;
    }

    public ArrayList<Project> getAllProjects(){
        return(ArrayList<Project>) iProjectRepository.findAll();
    }

    public Project getProjectById(Long id){
        Project project;
        project = iProjectRepository.findById(id).orElseThrow();
        return project;
    }

    public List<Project> getProjectsByCompletedStatus(boolean completed) {
        return iProjectRepository.findByCompleted(completed);
    }

    public Project createProject(Project newProject){
        return iProjectRepository.save(newProject);
    }

    public void updateProject(Project project){
        iProjectRepository.save(project);
    }

    public String deleteProject(Long id){
        try{
            iProjectRepository.deleteById(id);
            return "Delete successful";
        }catch (Exception error){
            return "Project not Found";
        }
    }
}
