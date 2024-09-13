package com.SCRUM.APP.service;

import com.SCRUM.APP.model.User;
import com.SCRUM.APP.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private IUserRepository iuserRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setPassword("password");
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(iuserRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(iuserRepository, times(1)).save(user);
    }

    @Test
    public void testGetAllUsers() {
        User user = new User();
        List<User> users = Collections.singletonList(user);
        when(iuserRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(iuserRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        when(iuserRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(iuserRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUsername");
        when(iuserRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        updatedUser.setUsername("NewUsername");
        updatedUser.setPassword("NewPassword");
        when(passwordEncoder.encode("NewPassword")).thenReturn("NewPassword");
        when(iuserRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedUser);
        assertEquals("NewUsername", result.getUsername());
        assertEquals("NewPassword", result.getPassword());
        verify(iuserRepository, times(1)).save(existingUser);
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(iuserRepository).deleteById(1L);

        userService.deleteUser(1L);
        verify(iuserRepository, times(1)).deleteById(1L);
    }
}
