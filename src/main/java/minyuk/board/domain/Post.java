package minyuk.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<UploadFile> attachFiles = new ArrayList<>();

    private String title;

    private String contents;

    private Long viewCount;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    //==생성 메서드==//
    public static Post createPost(User user, String title, String contents, List<UploadFile> attachFiles) {
        Post post = new Post();
        post.setUser(user);
        for (UploadFile attachFile: attachFiles) {
            post.addAttachFile(attachFile);
        }
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
        this.viewCount++;
    }

    //==연관관계 메소드==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void addAttachFile(UploadFile attachFile) {
        attachFiles.add(attachFile);
        attachFile.setPost(this);
    }

}
