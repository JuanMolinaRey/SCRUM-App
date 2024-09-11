package com.SCRUM.APP.dtos.task;


import com.SCRUM.APP.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    private final ModelMapper modelMapper;

    public TaskConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public TaskDTO taskToDto (Task task){
        return modelMapper.map (task, TaskDTO.class);
    }
    public Task dtoToTask (TaskDTO taskDTO){
        return modelMapper.map (taskDTO, Task.class);
    }
}
