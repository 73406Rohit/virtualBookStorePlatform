package com.VirtualBookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
//    private Set<RoleType> roles= new HashSet<>();

}
