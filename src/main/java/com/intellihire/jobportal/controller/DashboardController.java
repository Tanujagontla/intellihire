package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN":
                    return "redirect:/admin/dashboard";
                case "ROLE_EMPLOYEE":
                    return "redirect:/employee/dashboard";
                case "ROLE_CANDIDATE":
                    return "redirect:/candidate/dashboard";
                default:
                    return "redirect:/";
            }
        }
        return "redirect:/";
    }
}
