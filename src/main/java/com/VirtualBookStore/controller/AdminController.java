package com.VirtualBookStore.controller;

import com.VirtualBookStore.dto.RoleType;
import com.VirtualBookStore.dto.RoleUpdateRequest;
import com.VirtualBookStore.dto.User;
import com.VirtualBookStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PutMapping("/update/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateRole(@RequestBody RoleUpdateRequest request) {
        User updatedUser = userService.updateUserRole(request.getEmail(), request.getRole());
        return ResponseEntity.ok(updatedUser);
    }
}