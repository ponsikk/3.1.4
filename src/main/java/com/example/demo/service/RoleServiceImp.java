package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public Role getRoleById(long id) {
        return roleRepository.getById(id);
    }


    @Override
    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public RoleRepository getRoleRepository(){
        return roleRepository;
    }
}