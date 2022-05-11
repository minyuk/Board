package minyuk.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String contents;

    private Long viewCount;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    //==생성 메서드==//
    public static Post createPost(User user, String title, String contents) {
        Post post = new Post();
        post.setUser(user);

        post.setTitle(title);
        post.setContents(contents);
        post.setViewCount(0L);
        post.setCreateAt(LocalDateTime.now());
        post.setUpdateAt(LocalDateTime.now());

        return post;
    }

    public void changeParam(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.updateAt = LocalDateTime.now();
    }

    public void addCount() {
        this.viewCount += 1;
    }

    //==연관관계 메소드==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

}
