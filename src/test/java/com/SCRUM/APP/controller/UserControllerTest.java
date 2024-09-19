package com.SCRUM.APP.controller;

import com.SCRUM.APP.model.ERole;
import com.SCRUM.APP.model.User;
import com.SCRUM.APP.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.SCRUM.APP.model.ERole.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user1 = new User(1L, "User1", "user1@example.com", "password1", USER, null, null);
        user2 = new User(2L, "User2", "user2@example.com", "password2", USER, null, null);
    }

    @Test
    void create_User() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        String userJson = new ObjectMapper().writeValueAsString(user1);

        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(userJson));
    }

    @Test
    void get_All_Users() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        String usersJson = new ObjectMapper().writeValueAsString(List.of(user1, user2));

        mockMvc.perform(get("/api/v1/users/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(usersJson));
    }

    @Test
    void get_User_By_Id() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user1));

        String userJson = new ObjectMapper().writeValueAsString(user1);

        mockMvc.perform(get("/api/v1/users/list/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    void update_User() throws Exception {
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user1);

        String userJson = new ObjectMapper().writeValueAsString(user1);

        mockMvc.perform(put("/api/v1/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    void delete_User() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/v1/users/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }
}
