package com.Springbootlogin;
//
//import  org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
////import com.abc.thymleafex.model.Login;
//
//@Controller
//public class LoginController {
// @GetMapping("/login")
// public String showLogin() {
//  return "login";
// }
//
// //Check for Credentials
// @PostMapping("/login")
// public String login(@ModelAttribute(name="loginForm") Login login, Model m) {
//  String uname = login.getUsername();
//  String pass = login.getPassword();
//  if(uname.equals("Admin") && pass.equals("Admin@123")) {
//   m.addAttribute("uname", uname);
//   m.addAttribute("pass", pass);
//   return "welcome";
//  }
//  m.addAttribute("error", "Incorrect Username & Password");
//  return "login";
//  
// }
//}


import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    // Assuming you have a list of predefined users
    private List<User> users = Arrays.asList(
            new User("Admin", "Admin@123")
    );

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("loginForm", new User("username", "pass"));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") User loginForm, Model model, HttpSession session) {
        for (User user : users) {
            if (user.getUsername().equals(loginForm.getUsername()) && user.getPass().equals(loginForm.getPass())) {
                // Store user details in the session
                session.setAttribute("user", user);
                return "redirect:/home";
            }
        }

        model.addAttribute("error", "Incorrect Username & Password");
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // Check if the user is authenticated by checking the session
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "home";
        }
        // Redirect to login if the user is not authenticated
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Remove the user session attribute to log the user out
        session.removeAttribute("user");
        return "redirect:/login";
    }
}

