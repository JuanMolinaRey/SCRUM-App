package com.SCRUM.APP.controller;

import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.service.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;



    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void Test_Get_All_Projects() throws Exception {

        when(projectService.getAllProjects()).thenReturn(new ArrayList<>());


        mockMvc.perform(get("/api/v1/projects/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void Test_Get_Project_By_Id() throws Exception {

        Project project = new Project(1L, "hola", "pepe", false, null, null);

        when(projectService.getProjectById(1L)).thenReturn(project);


        mockMvc.perform(get("/api/v1/projects/list/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void Test_Get_Projects_By_CompletedStatus_With_Projects() throws Exception {

        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "atlantis", "sea", true, null, null));
        projects.add(new Project(2L, "odisea", "sea", true, null, null));

        when(projectService.getProjectsByCompletedStatus(true)).thenReturn(projects);


        mockMvc.perform(get("/api/v1/projects/list/completed/{completed}", true)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("atlantis"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("odisea"));


        verify(projectService, times(1)).getProjectsByCompletedStatus(true);
    }

    @Test
    void Test_Get_Projects_By_No_CompletedStatus_With_Projects() throws Exception {

        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "atlantis", "sea", false, null, null));
        projects.add(new Project(2L, "odisea", "sea", false, null, null));

        when(projectService.getProjectsByCompletedStatus(false)).thenReturn(projects);


        mockMvc.perform(get("/api/v1/projects/list/completed/{completed}", false)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("atlantis"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("odisea"));


        verify(projectService, times(1)).getProjectsByCompletedStatus(false);
    }

    @Test
    public void test_Create_Project() {
        Long id = 1L;
        Project project = new Project(1L, "amparo", "paro", false, null, null);
        project.setId(id);

        projectService.createProject(project);

        verify(projectService, times(1)).createProject(project);
    }



    @Test
    public void test_Update_Project() {
        Long id = 1L;
        Project project = new Project(1L, "odisea", "sea", false, null, null);
        project.setId(id);

        projectService.updateProject(project);

        verify(projectService, times(1)).updateProject(project);
    }

    @Test
    void delete_Project() throws Exception {
        long projectId = 1L;

        mockMvc.perform(delete("/api/v1/projects/delete/{id}", projectId))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(projectId);
    }

}
