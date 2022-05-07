package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import minyuk.board.controller.form.LoginForm;
import minyuk.board.domain.User;
import minyuk.board.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute LoginForm form) {
        return "user/loginForm";
    }

    @PostMapping
    public String login(@Valid @ModelAttribute LoginForm form, @RequestParam(defaultValue = "/") String redirectURL, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }

        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/loginForm";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원정보 보관
        session.setAttribute("loginUser", loginUser);


        return "redirect:" + redirectURL;
    }

}
