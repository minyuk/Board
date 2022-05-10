package minyuk.board.service;

import minyuk.board.domain.Post;
import minyuk.board.domain.User;
import minyuk.board.repository.PostRepository;
import minyuk.board.repository.PostSearch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시물등록() throws Exception {
        //given
        User user = createUser();
        Post post = createPost("test", "testing...");

        //when
        Long postId = postService.uploadPost(user.getId(), post.getTitle(), post.getContents());

        //then
        Post findPost = postRepository.findOne(postId);

        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getContents(), findPost.getContents());
    }

    @Test
    public void 게시물수정() throws Exception {
        //given
        User user = createUser();
        Post post = createPost("test", "testing...");

        //when
        Long postId = postService.uploadPost(user.getId(), post.getTitle(), post.getContents());
        postService.updatePost(postId, "test123", "modify");
        Post findPost = postRepository.findOne(postId);

        //then
        assertEquals("test123", findPost.getTitle());
        assertEquals("modify", findPost.getContents());
        assertNotEquals(post.getTitle(), findPost.getTitle());
        assertNotEquals(post.getContents(), findPost.getContents());
        assertNotEquals(findPost.getCreateAt(), findPost.getUpdateAt());
     }
     
    @Test
    public void 게시물삭제() throws Exception {
     //given
     User user = createUser();
     Post post = createPost("test", "testing...");

     //when
     Long postId = postService.uploadPost(user.getId(), post.getTitle(), post.getContents());
     postService.removePost(postId);

     //then
     Assertions.assertNull(postRepository.findOne(postId));
    }

    @Test
    public void 게시물검색() throws Exception {
        /*//given
        User user = createUser();
        Post post = createPost("test", "testing...");
        Post post2 = createPost("test2", "testing...");
        Post post3 = createPost("test3", "testing...");
        PostSearch ps = new PostSearch();
        ps.setUserName("mh");
        PostSearch ps2 = new PostSearch();
        ps2.setTitle(post.getTitle());
        PostSearch ps3 = new PostSearch();
        ps3.setUserName("mmm");

        //when
        Long postId = postService.uploadPost(user.getId(), post.getTitle(), post.getContents());
        Long postId2 = postService.uploadPost(user.getId(), post2.getTitle(), post2.getContents());
        Long postId3 = postService.uploadPost(user.getId(), post3.getTitle(), post3.getContents());
        List<Post> findPost = postRepository.findAll(ps);
        List<Post> findPost2 = postRepository.findAll(ps2);
        List<Post> findPost3 = postRepository.findAll(ps3);


        //then
        Assertions.assertEquals(3, findPost.size());
        Assertions.assertEquals(1, findPost2.size());
        Assertions.assertEquals(0, findPost3.size());*/

     }


         


    private Post createPost(String title, String contents) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        em.persist(post);

        return post;
    }


     private User createUser() {
         User user = new User();
         user.setLoginId("kmh1234");
         user.setPassword("1234");
         user.setName("mh");
         em.persist(user);

         return user;
     }


}