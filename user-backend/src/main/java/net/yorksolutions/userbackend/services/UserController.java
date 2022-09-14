package net.yorksolutions.userbackend.services;

import net.yorksolutions.userbackend.entity.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class UserController {

    //    private final QuizService quizService;
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

    @GetMapping("/admin-create")
    public void create(@RequestParam UUID token, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        if (!this.userService.checkAuth(token, "admin")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an admin");
        }else {
            this.userService.adminCreate(username, password, role);
        }
    }

    @GetMapping("/create")
    public void create(@RequestParam String username, @RequestParam String password) {
//        if(!this.userService.checkAuth())
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not and admin");
        this.userService.create(username, password);
    }

    @PostMapping("/edit")
    public void edit(@RequestParam UUID token, @RequestBody UserInfo userInfo) {
        if (!this.userService.checkAuth(token, "admin"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not and admin");
        this.userService.edit(userInfo);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam UUID token) {
        if (!this.userService.checkAuth(token, "admin"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not and admin");
        this.userService.delete(token);
    }

//    @GetMapping("/checkAuth/{token}")
//    public Boolean checkAuth(UUID token){
//        if(!tokenMap.containsKey(token)){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
