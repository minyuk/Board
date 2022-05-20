package minyuk.board.controller.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PostForm {

    @NotEmpty
    private String title;

    private String contents;

    private List<MultipartFile> attachFiles;

}
