package com.example.demo.controller;

import com.example.demo.model.User;


import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping({"", "list"})
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());

        model.addAttribute("showUserProfile",
                model.containsAttribute("user") && !((User) model.getAttribute("user")).isNew());
        model.addAttribute("showNewUserForm",
                model.containsAttribute("user") && ((User) model.getAttribute("user")).isNew());
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }

        return "admin-page";
    }

    @GetMapping("/{id}/profile")
    public String showUserProfileModal(@PathVariable("id") Long userId, Model model) {
        try {
            model.addAttribute("allRoles", roleService.getAllRoles());
            model.addAttribute("user", userService.getUserById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId)));
            return "fragments/user-form";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PatchMapping()
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/admin";
        }
        userService.updateUser(user.getId(), user);
        redirectAttributes.addFlashAttribute("success", "User updated successfully!");
        return "redirect:/admin";
    }

    @DeleteMapping("")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }

    @PostMapping()
    public String insertUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/admin";
        }
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "User created successfully!");
        return "redirect:/admin";
    }
}
