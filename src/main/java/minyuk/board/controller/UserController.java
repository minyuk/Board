package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minyuk.board.controller.form.UserForm;
import minyuk.board.domain.User;
import minyuk.board.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
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
    public String create(@Validated UserForm form, BindingResult bindingResult) {

        if (form.getPassword() != null && form.getPasswordCheck() != null) {
            if (!form.getPassword().equals(form.getPasswordCheck())) {
                bindingResult.reject("passwordMismatch", null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors= {}", bindingResult);
            return "user/joinForm";
        }

        User user = new User();
        user.setLoginId(form.getLoginId());
        user.setPassword(form.getPassword());
        user.setName(form.getName());

        userService.join(user);

        return "redirect:/login";
    }
}
