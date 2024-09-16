package com.SCRUM.APP.repository;

import com.SCRUM.APP.model.Status;
import com.SCRUM.APP.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ITaskRepository extends JpaRepository<Task, Long> {
//    Optional<Task> getTaskByStatus(Status status);
}
