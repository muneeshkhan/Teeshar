package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    // Root -> index.html
    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    // Handle form.html (simple username submit)
    @PostMapping("/submit")
    public String handleSubmit(@RequestParam String username, Model model) {
        model.addAttribute("name", username);
        return "result";
    }

    // Show register form
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    // Handle register form
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);

        model.addAttribute("name", username);
        return "result";
    }

    // Show login form
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    // Handle login form
    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("name", user.getUsername());
        } else {
            model.addAttribute("name", "Login Failed");
        }
        return "result";
    }
}
