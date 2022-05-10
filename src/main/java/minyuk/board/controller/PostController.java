package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.Post;
import minyuk.board.domain.User;
import minyuk.board.login.argumentresolver.Login;
import minyuk.board.repository.PostSearch;
import minyuk.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String home(@Login User loginUser, @ModelAttribute PostSearch postSearch, Model model) {
        model.addAttribute("user", loginUser);

        List<Post> posts = postService.findPosts(postSearch);
        model.addAttribute("posts", posts);

        return "post/postList";
    }

    @GetMapping("/post/new")
    public String add(Model model) {
        return "post/addPost";
    }

}
