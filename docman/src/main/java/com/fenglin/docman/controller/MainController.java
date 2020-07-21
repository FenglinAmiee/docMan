package com.fenglin.docman.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = {"/", "/index"})
    public String home() {
        return "home";
    }

    @RequestMapping("/main")
    public String index(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null) {
            model.addAttribute("currentUser", user);
        }

        return "main";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "home";
    }

    @RequestMapping("/user")
    public String toUserPage(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null) {
            model.addAttribute("currentUser", user);
        }
        return "user";
    }

    @RequestMapping("/docs")
    public String toDocsPage(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null) {
            model.addAttribute("currentUser", user);
        }

        return "docs";
    }

    @RequestMapping("/doLogin")
    public String doLogin(Model model) {
        model.addAttribute("currentUser", (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        return "main";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
