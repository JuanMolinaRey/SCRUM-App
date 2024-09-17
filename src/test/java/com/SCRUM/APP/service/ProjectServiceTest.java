package com.SCRUM.APP.service;

import com.SCRUM.APP.model.Project;
import com.SCRUM.APP.repository.IProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectService projectService;
    private IProjectRepository iProjectRepository;

    @BeforeEach
    public void setUp() {
        iProjectRepository = mock(IProjectRepository.class);
        projectService = new ProjectService(iProjectRepository);
    }

    @Test
    public void test_Get_All_Projects() {

        List<Project> mockProjects = new ArrayList<>();
        mockProjects.add(new Project(1L, "Project 1", "pepe", false, null, null));
        mockProjects.add(new Project(1L, "Project 2", "neo", false, null, null));

        when(iProjectRepository.findAll()).thenReturn(mockProjects);

        ArrayList<Project> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getName());
        assertEquals("Project 2", result.get(1).getName());

        verify(iProjectRepository, times(1)).findAll();
    }

    @Test
    public void test_Get_Project_By_Id() {

        Project mockProject = new Project(1L, "Project 1", "pepe", false, null, null);
        Long projectId = 1L;

        when(iProjectRepository.findById(projectId)).thenReturn(Optional.of(mockProject));

        Project result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals("Project 1", result.getName());

        verify(iProjectRepository, times(1)).findById(projectId);
    }

    @Test
    public void test_Get_Projects_By_Completed_Status() {

        List<Project> mockProjects = new ArrayList<>();
        mockProjects.add(new Project(1L, "Project 1", "Ulises", true, null, null));
        mockProjects.add(new Project(2L, "Project 2", "neo", true, null, null));


        when(iProjectRepository.findByCompleted(true)).thenReturn(mockProjects);


        List<Project> result = projectService.getProjectsByCompletedStatus(true);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).isCompleted());
        assertTrue(result.get(1).isCompleted());


        verify(iProjectRepository, times(1)).findByCompleted(true);
    }

    @Test
    public void test_Create_Project() {

        Project newProject = new Project(1L, "Sistema Divino", "evangelion", false, null, null);

        when(iProjectRepository.save(newProject)).thenReturn(newProject);

        Project result = projectService.createProject(newProject);

        assertNotNull(result);
        assertEquals("Sistema Divino", result.getName());

        verify(iProjectRepository, times(1)).save(newProject);
    }

    @Test
    public void testUpdateProject() {

        Project project = new Project(1L, "Sistema Divino", "evangelion", false, null, null);
        project.setId(1L);

        projectService.updateProject(project);

        verify(iProjectRepository, times(1)).save(project); // Verificas que se haya llamado al método save del repositorio una vez
    }
    @Test
    public void test_Delete_Project_Success() {

        Long projectId = 1L;

        String result = projectService.deleteProject(projectId);

        verify(iProjectRepository, times(1)).deleteById(projectId);

        assertEquals("Delete successful", result);
    }

    @Test
    public void test_Delete_if_Project_Not_Found() {

        Long projectId = 1L;
        doThrow(new RuntimeException("Project not found")).when(iProjectRepository).deleteById(projectId);

        String result = projectService.deleteProject(projectId);

        verify(iProjectRepository, times(1)).deleteById(projectId);

        assertEquals("Project not Found", result);
    }
}