package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minyuk.board.controller.form.PostForm;
import minyuk.board.domain.Post;
import minyuk.board.domain.User;
import minyuk.board.login.argumentresolver.Login;
import minyuk.board.repository.PostSearch;
import minyuk.board.service.PostService;
import minyuk.board.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    @GetMapping("/posts")
    public String home(@Login User loginUser, @ModelAttribute PostSearch postSearch, Model model) {
        model.addAttribute("user", loginUser);

        List<Post> posts = postService.findPosts(postSearch);
        model.addAttribute("posts", posts);

        return "post/postList";
    }

    @GetMapping("/posts")
    public String home(@Login User loginUser, @ModelAttribute PostSearch postSearch,
                       @PageableDefault Pageable pageable,
                       Model model) {
        model.addAttribute("user", loginUser);

        Page<Post> posts = postService.findPosts(postSearch, pageable);
        model.addAttribute("posts", posts);

        return "post/postList";
    }

    @GetMapping("/post/add")
    public String createForm(@ModelAttribute PostForm postForm) {
        return "post/addPost";
    }

    @PostMapping("/post/add")
    public String addPost(@Login User loginUser, @Validated @ModelAttribute PostForm postForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors= {}", bindingResult);
            return "post/addPost";
        }

        //게시물 등록
        postService.uploadPost(loginUser.getId(), postForm.getTitle(), postForm.getContents());

        return "redirect:/posts";
    }

    @GetMapping("/post/{postId}/edit")
    public String post(@Login User loginUser,
                       @PathVariable long postId,
                       Model model) {
        Post post = postService.findOne(postId);
        postService.addViewCount(post.getId());
        model.addAttribute("post", post);

        //로그인 한 사용자와 작성자가 다를 경우
        if (loginUser.getId() != post.getUser().getId()) {
            return "post/detailPost";
        }

        //로그인 한 사용자와 작성자가 같을 경우
        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContents(post.getContents());
        model.addAttribute("postForm", postForm);
        return "post/detailPostEdit";
    }

    @PostMapping("/post/{postId}/edit")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostForm postForm) {
        postService.updatePost(postId, postForm.getTitle(), postForm.getContents());
        return "redirect:/posts";
    }

    @PostMapping("/post/{postId}/remove")
    public String cancelOrder(@PathVariable("postId") Long postId) {
        postService.removePost(postId);
        return "redirect:/posts";
    }

}
