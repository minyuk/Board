package minyuk.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minyuk.board.controller.form.PostForm;
import minyuk.board.domain.Post;
import minyuk.board.domain.UploadFile;
import minyuk.board.domain.User;
import minyuk.board.file.FileStore;
import minyuk.board.login.argumentresolver.Login;
import minyuk.board.repository.PostSearch;
import minyuk.board.repository.PostSort;
import minyuk.board.service.PostService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileStore fileStore;

    @GetMapping("/posts")
    public String home(@Login User loginUser,
                       @ModelAttribute PostSearch postSearch,
                       @ModelAttribute PostSort postSort,
                       @PageableDefault Pageable pageable,
                       Model model) {
        model.addAttribute("user", loginUser);

        Page<Post> posts = postService.findPosts(postSearch, pageable, postSort);
        model.addAttribute("posts", posts);

        return "post/postList";
    }

    @GetMapping("/post/add")
    public String createForm(@ModelAttribute PostForm postForm) {
        return "post/addPost";
    }

    @PostMapping("/post/add")
    public String addPost(@Login User loginUser, @Validated @ModelAttribute PostForm postForm, BindingResult bindingResult) throws IOException {

        List<UploadFile> attachFiles = fileStore.storeFiles(postForm.getAttachFiles());

        if (bindingResult.hasErrors()) {
            log.info("errors= {}", bindingResult);
            return "post/addPost";
        }

        //게시물 등록
        postService.uploadPost(loginUser.getId(), postForm.getTitle(), postForm.getContents(), attachFiles);

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

    @GetMapping("/attach/{uploadFileName}/{storeFileName}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable String uploadFileName,
                                                   @PathVariable String storeFileName) throws MalformedURLException {

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisPosition = "attachment; filename\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisPosition)
                .body(resource);
    }

}
