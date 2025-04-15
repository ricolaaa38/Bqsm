package controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcomePage(Model model) {
        OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String loggedInUserName = user.getAttribute("name");
        model.addAttribute("loggedInUserName", loggedInUserName);
        return "home";
    }
}
