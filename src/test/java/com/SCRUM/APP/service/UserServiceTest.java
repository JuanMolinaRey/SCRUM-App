package com.SCRUM.APP.service;

import com.SCRUM.APP.model.ERole;
import com.SCRUM.APP.model.User;
import com.SCRUM.APP.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User existingUser;
    private User newUser;
    private User updatedUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        existingUser = new User(1L, "oldUsername", "oldemail@example.com", "oldPassword", ERole.USER, List.of(), List.of());

        newUser = new User(null, "testUser", "test@example.com", "password", ERole.USER, List.of(), List.of());

        updatedUser = new User(1L, "updatedUsername", "updated@example.com", "newPassword", ERole.USER, List.of(), List.of());

    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(existingUser, newUser));

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("oldUsername", result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("updatedUsername", result.getUsername());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals("newPassword", result.getPassword());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("updatedUsername", savedUser.getUsername());
        assertEquals("updated@example.com", savedUser.getEmail());
        assertEquals("newPassword", savedUser.getPassword());
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(1L, updatedUser));

        assertEquals("User not found with id 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        doThrow(new RuntimeException("User not found with id 1")).when(userRepository).deleteById(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));

        assertEquals("User not found with id 1", exception.getMessage());
        verify(userRepository, times(1)).deleteById(1L);
    }
}
