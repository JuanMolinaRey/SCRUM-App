package com.SCRUM.APP.repository;

import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProjectRepository extends JpaRepository<Project, Long> {
//    Optional<Project> getProjectByStatus(Status status);
}
