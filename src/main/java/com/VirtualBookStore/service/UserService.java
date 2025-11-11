package com.VirtualBookStore.service;

import com.VirtualBookStore.dto.RoleType;
import com.VirtualBookStore.dto.User;

public interface UserService {
    User updateUserRole(String email, RoleType newRole);

}
