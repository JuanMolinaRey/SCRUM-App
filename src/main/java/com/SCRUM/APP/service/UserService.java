package com.SCRUM.APP.service;

import com.SCRUM.APP.model.User;
import com.SCRUM.APP.repository.IUserRepository;

import java.util.Optional;

public class UserService {

    private final IUserRepository iuserRepository;

    public UserService(IUserRepository iuserRepository) {
        this.iuserRepository = iuserRepository;
    }
    public Optional<User> getUserById(Long id) {
        return iuserRepository.findById(id);
    }
}
