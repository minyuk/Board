package minyuk.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<AttachFile> attachFiles = new ArrayList<>();

    private String title;

    private String contents;

    private Long viewCount;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;


    //==연관관계 메소드==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void setFile(AttachFile attachFile) {
        attachFiles.add(attachFile);
        attachFile.setPost(this);
    }

}
