package minyuk.board.service;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.Post;
import minyuk.board.domain.UploadFile;
import minyuk.board.domain.User;
import minyuk.board.repository.PostRepository;
import minyuk.board.repository.PostSearch;
import minyuk.board.repository.PostSort;
import minyuk.board.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //등록
    @Transactional
    public Long uploadPost(Long userId, String title, String contents, List<UploadFile> attachFiles) {
        //엔티티 조회
        User user = userRepository.findOne(userId);

        //게시물 생성
        Post post = Post.createPost(user, title, contents, attachFiles);

        //저장
        postRepository.save(post);

        return post.getId();
    }

    //수정
    @Transactional
    public void updatePost(Long postId, String title, String contents) {
        Post findPost = postRepository.findOne(postId);
        findPost.changeParam(title, contents);
    }

    //삭제
    @Transactional
    public void removePost(Long postId) {
        Post findPost = postRepository.findOne(postId);
        postRepository.remove(findPost);
    }

    @Transactional
    public void addViewCount(Long postId) {
        Post post = postRepository.findOne(postId);
        post.addCount();
    }


    //검색
    public List<Post> findPosts(PostSearch postSearch) {
        return postRepository.findAll(postSearch);
    }

    public Page<Post> findPosts(PostSearch postSearch, Pageable pageable, PostSort postSort) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10); // <- Sort 추가

        return postRepository.findAll(postSearch, pageable, postSort);
    }

    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }
}
