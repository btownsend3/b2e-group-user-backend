package net.yorksolutions.userbackend.services;

import org.springframework.lang.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;
    public UserController(@NonNull UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
    @GetMapping("/login")
    public UUID login(@RequestParam String username, @RequestParam String password) {
        return this.userService.login(username, password);
    }
    @GetMapping("/logout")
    public void logout(@RequestParam UUID token) {
        this.userService.logout(token);
    }
    @GetMapping("/create")
    public void create(@RequestParam String username, @RequestParam String password, @RequestParam List<String> permissions, @RequestParam String role) {
        this.userService.create(username, password, permissions, role);
    }
    @GetMapping("/edit")
    public void edit(@RequestParam String username, @RequestParam String password, @RequestParam List<String> permissions, @RequestParam String role) {
        this.userService.create(username, password, permissions, role);
    }
    @GetMapping("/delete")
    public void delete() {

    }
}
