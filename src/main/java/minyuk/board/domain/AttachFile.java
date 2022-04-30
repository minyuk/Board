package minyuk.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class AttachFile {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private FileInfo fileInfo;

    //==생성 메서드==//
    public static AttachFile createAttachFile(FileInfo fileInfo) {
        AttachFile attachFile = new AttachFile();
        attachFile.setFileInfo(fileInfo);

        return attachFile;
    }

    public void changeFile(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
}
