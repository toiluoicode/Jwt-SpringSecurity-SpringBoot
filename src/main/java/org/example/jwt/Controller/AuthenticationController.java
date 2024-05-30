package org.example.jwt.Controller;

import org.example.jwt.Models.User;
import org.example.jwt.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
    @GetMapping("/hello")
    public String hello (){
        return "helo";
    }
}

