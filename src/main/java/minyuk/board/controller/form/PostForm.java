package minyuk.board.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostForm {

    @NotEmpty
    private String title;

    private String contents;

}
