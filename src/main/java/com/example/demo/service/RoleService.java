package com.example.demo.service;


import com.example.demo.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();
    Role getRoleById(long id);

    void addRole( Role role);
}