package com.SCRUM.APP.controller;

import com.SCRUM.APP.model.User;
import com.SCRUM.APP.model.ERole;
import com.SCRUM.APP.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User validUser;
    private User updatedUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        validUser = new User(1L, "testUser", "test@example.com", "password", ERole.USER, Collections.emptyList(), Collections.emptyList());
        updatedUser = new User(1L, "updatedUser", "updated@example.com", "newPassword", ERole.ADMIN, Collections.emptyList(), Collections.emptyList());

    }

    @Test
    public void test_Create_User() throws Exception {
        given(userService.createUser(ArgumentMatchers.any(User.class))).willReturn(validUser);

        mockMvc.perform(post("/api/users/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(validUser.getId()))
                .andExpect(jsonPath("$.username").value(validUser.getUsername()))
                .andExpect(jsonPath("$.email").value(validUser.getEmail()));
    }

    @Test
    public void test_Get_AllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(Collections.singletonList(validUser));

        mockMvc.perform(get("/api/users/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"));
    }

    @Test
    public void test_Get_User_By_Id() throws Exception {
        given(userService.getUserById(1L)).willReturn(Optional.of(validUser));

        mockMvc.perform(get("/api/users/list/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void test_Get_User_By_Id_Not_Found() throws Exception {
        given(userService.getUserById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Update_User() throws Exception {
        given(userService.updateUser(1L, updatedUser)).willReturn(updatedUser);

        mockMvc.perform(put("/api/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"));
    }

    @Test
    public void test_Update_User_Not_Found() throws Exception {
        given(userService.updateUser(1L, updatedUser)).willThrow(new RuntimeException("User not found with id 1"));

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_User() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_Delete_User_Not_Found() throws Exception {
        doThrow(new RuntimeException("User not found with id 1")).when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Get_All_Users_When_No_Users() throws Exception {
        given(userService.getAllUsers()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
