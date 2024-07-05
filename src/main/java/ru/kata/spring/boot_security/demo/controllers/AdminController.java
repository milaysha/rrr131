package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@org.springframework.stereotype.Controller
class AdminController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin")
    public String getAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }


    @GetMapping("/get")
    public String get(@RequestParam(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "errorPage";
        }
        model.addAttribute("user", user);
        return "get";
    }


    @GetMapping(value = "/create")
    public String addUserForm(Model model) { //@ModelAttribute("user") User user) {
        model.addAttribute("user", new User());
        return "create";
    }


    @PostMapping("/createPost")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "userSaved";
    }



    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        if (userService.findById(id) == null) {
            return "errorPage";
        }
        userService.deleteById(id);
        return "deleteCompleted";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "changeCompleted";
    }


    @GetMapping("/completed")
    public String showChangeCompletedPage() {
        return "changeCompleted";
    }



}

