package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.User;
import minyuk.board.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/new")
    public String createForm(@ModelAttribute UserForm userForm) {
        return "user/joinForm";
    }

    @PostMapping("/new")
    public String create(UserForm form) {
        User user = new User();
        user.setLoginId(form.getLoginId());
        user.setPassword(form.getPassword());
        user.setName(form.getName());

        userService.join(user);

        return "redirect:/";
    }
}
