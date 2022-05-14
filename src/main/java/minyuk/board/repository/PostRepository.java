package minyuk.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minyuk.board.domain.Post;
import minyuk.board.domain.QPost;
import minyuk.board.domain.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
                .where(like(postSearch.getSearchName(), postSearch.getSearchKeyword()))
                .limit(1000)
                .fetch();

    }

    public Page<Post> findAll(PostSearch postSearch, Pageable pageable) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QPost post = QPost.post;
        QUser user = QUser.user;

        List<Post> posts = query
                .select(post)
                .from(post)
                .join(post.user, user)
                .where(like(postSearch.getSearchName(), postSearch.getSearchKeyword()))
                .orderBy(post.updateAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //count 쿼리 분리
        JPAQuery<Post> countQuery = query
                .select(post)
                .from(post)
                .join(post.user, user)
                .where(like(postSearch.getSearchName(), postSearch.getSearchKeyword()));

        return PageableExecutionUtils.getPage(posts, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression like(String name, String keyword) {
        if (keyword != null && StringUtils.hasText(name)) {
            if (keyword.equals("name")) {
                return QUser.user.name.like(name);
            } else if (keyword.equals("title")) {
                return QPost.post.title.like(name);
            }
        }

        return null;
    }

}
