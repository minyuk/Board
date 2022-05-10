package minyuk.board.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class PostSearch {

    private String searchName;

    @Enumerated(EnumType.STRING)
    private SearchKeyword searchKeyword;

}
