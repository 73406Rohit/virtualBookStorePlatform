package com.VirtualBookStore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUpdateRequest {
    private String email;
    private RoleType role;
}
