package minyuk.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minyuk.board.domain.Post;
import minyuk.board.domain.QPost;
import minyuk.board.domain.QUser;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public void remove(Post post) {
        em.remove(post);
    }

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll(PostSearch postSearch) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QPost post = QPost.post;
        QUser user = QUser.user;

        return query
                .select(post)
                .from(post)
                .join(post.user, user)
                .where(searchLike(postSearch.getSearchName(), postSearch.getSearchKeyword()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression searchLike(String name, SearchKeyword keyWord) {
        log.info("keyword={}", keyWord);
        log.info("name={}", name);
        if (keyWord == null || !StringUtils.hasText(name)) {
            return null;
        }

        //return QPost.post.title.like(name);
        return QUser.user.name.like(name);
    }

}
