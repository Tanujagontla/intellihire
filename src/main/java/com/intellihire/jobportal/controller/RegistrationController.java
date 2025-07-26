package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.model.Candidate;
import com.intellihire.jobportal.model.Role;
import com.intellihire.jobportal.service.CandidateService;
import com.intellihire.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("candidate") Candidate candidate, 
                              BindingResult result, 
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            return "register";
        }

        // Check if username already exists
        if (userService.existsByUsername(candidate.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        // Check if email already exists
        if (userService.existsByEmail(candidate.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        try {
            // Set default role as CANDIDATE for new registrations
            candidate.setRole(Role.CANDIDATE);
            
            // Save the candidate using userService to ensure password encoding
            userService.saveUser(candidate);
            
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! You can now login with your credentials.");
            return "redirect:/login";
            
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}
