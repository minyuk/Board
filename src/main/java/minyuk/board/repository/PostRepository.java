package minyuk.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minyuk.board.domain.Post;
import minyuk.board.domain.QPost;
import minyuk.board.domain.QUser;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

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
                .where(nameLike(postSearch.getUserName()),
                        titleLike(postSearch.getTitle()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression nameLike(String nameCond) {
        if (!StringUtils.hasText(nameCond)) {
            return null;
        }

        return QUser.user.name.like(nameCond);
    }

    private BooleanExpression titleLike(String titleCond) {
        if (!StringUtils.hasText(titleCond)) {
            return null;
        }

        return QPost.post.title.like(titleCond);
    }



}
