package com.SCRUM.APP.service;

import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.repository.IProjectRepository;

import java.util.Optional;

public class ProjectService {

    private final IProjectRepository projectRepository;

    public ProjectService(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
}
