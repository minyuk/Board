package minyuk.board.controller.form;

import minyuk.board.domain.User;
import minyuk.board.login.argumentresolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@Login User loginUser) {
        if (loginUser == null) {
            return "redirect:/login";
        }

        return "redirect:/posts";
    }
}
