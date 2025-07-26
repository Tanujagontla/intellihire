package com.intellihire.jobportal.controller.api;

import com.intellihire.jobportal.model.Candidate;
import com.intellihire.jobportal.model.Role;
import com.intellihire.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Candidate candidate){
        if(userService.existsByUsername(candidate.getUsername())){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if(userService.existsByEmail(candidate.getEmail())){
            return ResponseEntity.badRequest().body("Email already exists");
        }
        candidate.setRole(Role.CANDIDATE);
        // Password will be encoded in UserService, avoid double encoding here
        // Just pass the raw password to be encoded once in service layer
        userService.saveUser(candidate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getPrincipal());
    }
}
