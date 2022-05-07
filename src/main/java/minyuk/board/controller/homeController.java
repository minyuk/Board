package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import minyuk.board.login.argumentresolver.Login;
import minyuk.board.domain.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class homeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {

        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("user", loginUser);
        return "redirect:/posts";
    }
}
