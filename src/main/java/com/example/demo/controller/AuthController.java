package com.example.demo.controller;


import com.example.demo.model.User;

import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }

    @GetMapping("/login")
    public String welcomePage(Model model, HttpSession session,
                              @SessionAttribute(required = false, name = "Authentication-Name") String authenticationName) {
        if (authenticationName != null) {
            User user = userService.getAllUsers()
                    .stream()
                    .filter(u -> u.getUsername().equals(authenticationName))
                    .findFirst()
                    .orElse(null);
            if (user != null) {
                session.setAttribute("user", user);
                model.addAttribute("authenticated", true);
                model.addAttribute("user", user);
            } else {
                session.invalidate();
            }
        } else {
            session.invalidate();
            model.addAttribute("authenticated", false);
        }

        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied-page";
    }
}