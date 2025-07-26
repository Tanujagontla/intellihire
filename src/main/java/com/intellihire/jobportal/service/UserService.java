package com.intellihire.jobportal.service;

import com.intellihire.jobportal.model.Role;
import com.intellihire.jobportal.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllUsers();
    List<User> findByRole(Role role);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User updateUser(User user);
    Long countByRole(Role role);
}
