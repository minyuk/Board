package minyuk.board.domain;

import lombok.Data;

@Data
public class FileInfo {

    private String uploadFileName;
    private String storeFileName;

    public FileInfo(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
