package minyuk.board.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordCheck;

    @NotEmpty
    private String name;
}
