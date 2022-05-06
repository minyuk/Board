package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.User;
import minyuk.board.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute LoginForm form) {
        return "user/loginForm";
    }

    @PostMapping
    public String login(@ModelAttribute LoginForm form) {

        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            return "/";
        }

        return "redirect:/posts";
    }

}
