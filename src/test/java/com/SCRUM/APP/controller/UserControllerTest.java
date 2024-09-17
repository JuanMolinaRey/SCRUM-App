package com.SCRUM.APP.controller;

import com.SCRUM.APP.model.ERole;
import com.SCRUM.APP.model.User;
import com.SCRUM.APP.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
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

    private MockMvc mockController;
    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockController = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User(1L, "john_doe","john@example.com","password",USER, null, null);
        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);

        String userJson = "{"
                + "\"id\": 1,"
                + "\"username\": \"john_doe\","
                + "\"email\": \"john@example.com\","
                + "\"password\": \"password\","
                + "\"role\": \"USER\""
                + "}";

        mockController
                .perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("{"
                        + "\"id\": 1,"
                        + "\"username\": \"john_doe\","
                        + "\"email\": \"john@example.com\","
                        + "\"password\": \"password\","
                        + "\"role\": \"USER\""
                        + "}"));
    }


    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userList);

        String userJson = "["
                + "{"
                + "\"id\": 1,"
                + "\"username\": \"john_doe\","
                + "\"email\": \"john@example.com\","
                + "\"password\": \"password\","
                + "\"role\": \"USER\""
                + "}]";

        mockController.perform(get("/api/v1/users/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        String userJson = "{"
                + "\"id\": 1,"
                + "\"username\": \"john_doe\","
                + "\"email\": \"john@example.com\","
                + "\"password\": \"password\","
                + "\"role\": \"USER\""
                + "}";

        mockController.perform(get("/api/v1/users/list/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User(1L, "john_doe","john@example.com","password",USER, null, null);
        updatedUser.setId(1L);
        updatedUser.setUsername("john_doe_updated");
        updatedUser.setEmail("john_updated@example.com");
        updatedUser.setPassword("new_password");
        updatedUser.setRole(ERole.ADMIN);

        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(updatedUser);

        String userJson = "{"
                + "\"id\": 1,"
                + "\"username\": \"john_doe_updated\","
                + "\"email\": \"john_updated@example.com\","
                + "\"password\": \"new_password\","
                + "\"role\": \"ADMIN\""
                + "}";

        mockController.perform(put("/api/v1/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockController.perform(delete("/api/v1/users/delete/1"))
                .andExpect(status().isNoContent());
    }
}
