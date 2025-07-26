package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test/users")
    @ResponseBody
    public String testUsers() {
        StringBuilder result = new StringBuilder();
        result.append("User Test Results:\n");
        
        var admin = userService.findByUsername("admin");
        result.append("Admin exists: ").append(admin.isPresent()).append("\n");
        
        var hrManager = userService.findByUsername("hr_manager");
        result.append("HR Manager exists: ").append(hrManager.isPresent()).append("\n");
        
        var candidate1 = userService.findByUsername("candidate1");
        result.append("Candidate1 exists: ").append(candidate1.isPresent()).append("\n");
        
        if (candidate1.isPresent()) {
            result.append("Candidate1 role: ").append(candidate1.get().getRole()).append("\n");
            result.append("Password matches 'password': ")
                  .append(passwordEncoder.matches("password", candidate1.get().getPassword())).append("\n");
        }
        
        return result.toString();
    }
}
