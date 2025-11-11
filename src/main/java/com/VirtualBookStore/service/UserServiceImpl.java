package com.VirtualBookStore.service;

import com.VirtualBookStore.dao.UserRepository;
import com.VirtualBookStore.dto.RoleType;
import com.VirtualBookStore.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User updateUserRole(String email, RoleType newRole) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Simply set the new role
        user.setRole(newRole);
        return userRepository.save(user);
    }
}