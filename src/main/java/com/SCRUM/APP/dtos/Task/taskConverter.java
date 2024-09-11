package com.SCRUM.APP.dtos.Task;

import org.springframework.stereotype.Component;

@Component
public class taskConverter {



    private final ModelMapper modelMapper;

    public TaskConverter (ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
}
