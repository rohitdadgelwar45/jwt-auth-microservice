package com.example.demo.util;

import java.util.List;

import com.example.demo.enums.Role;

public class RoleScopeMapper {

    public static List<String> getScopes(Role role){

        if(role == Role.ADMIN){

            return List.of(
                    "USER_READ",
                    "USER_WRITE",
                    "ADMIN_READ",
                    "ADMIN_WRITE"
            );
        }

        return List.of(
                "USER_READ"
        );
    }
}