package com.SCRUM.APP.service;

import com.SCRUM.APP.model.User;
import com.SCRUM.APP.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository iuserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(IUserRepository iuserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.iuserRepository = iuserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return iuserRepository.save(user);
    }

    public List<User> getAllUsers() {
        return iuserRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return iuserRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        if (userDetails.getUsername() == null || userDetails.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        return iuserRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword());
                    user.setRole(userDetails.getRole());
                    user.setTasks(userDetails.getTasks());
                    //user.setProjectsList(userDetails.getProjectsList());
                    return iuserRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        iuserRepository.deleteById(id);
    }
}