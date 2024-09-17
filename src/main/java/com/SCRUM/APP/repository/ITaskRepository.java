package com.SCRUM.APP.repository;

import com.SCRUM.APP.model.Status;
import com.SCRUM.APP.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ITaskRepository extends JpaRepository<Task, Long> {
}
