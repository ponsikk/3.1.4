package com.example.demo.controller;
import com.example.demo.model.User;
import com.example.demo.service.RoleServiceImp;
import com.example.demo.service.UserServiceImp;

import com.example.demo.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController // rework
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userService; // Тут тоже реализация Imp
    private final UserValidator userValidator;
    private final RoleServiceImp roleService; // Теперь зависим от реализаций

    @Autowired
    public AdminController(UserServiceImp userService, UserValidator userValidator, RoleServiceImp roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/admin";
    }

    @GetMapping(value = "/addUser")
    public String addsUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("users",userService.getAllUsers());
        return "admin/addUser";
    }


    @PostMapping("/addUser")
    public String addsUser(Model model, @RequestBody User user, @RequestParam BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "admin/addUser";
        }
        userService.saveUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return  "redirect:/admin";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(Model model, @RequestParam Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("users",userService.getAllUsers());
        return "admin/editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@RequestBody User user, @RequestParam Long id){
        userService.updateUser(id,user);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete/{id}")
    private String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
