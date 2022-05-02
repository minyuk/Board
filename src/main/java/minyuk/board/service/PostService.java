package minyuk.board.service;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.Post;
import minyuk.board.domain.User;
import minyuk.board.repository.PostRepository;
import minyuk.board.repository.PostSearch;
import minyuk.board.repository.UserRepository;
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
    public Long uploadPost(Long userId, String title, String contents) {
        //엔티티 조회
        User user = userRepository.findOne(userId);

        //게시물 생성
        Post post = Post.createPost(user, title, contents);

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

    //검색
    public List<Post> findPosts(PostSearch postSearch) {
        return postRepository.findAll(postSearch);
    }
}
