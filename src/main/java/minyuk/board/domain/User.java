package minyuk.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
}
