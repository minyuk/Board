package minyuk.board.service;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.AttachFile;
import minyuk.board.domain.FileInfo;
import minyuk.board.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public void saveFile(AttachFile attachFile) {
        fileRepository.save(attachFile);
    }

    //==파일명 및 경로 생성하기==//
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public FileInfo storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        //서버에 저장하는 파일명
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new FileInfo(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
    //==파일명 및 경로 생성하기==//

    @Transactional
    public void updateFile(Long fileId, FileInfo fileInfo) {
        AttachFile findFile = fileRepository.findOne(fileId);
        findFile.changeFile(fileInfo);
    }

    public List<AttachFile> findFiles() {
        return fileRepository.findAll();
    }

    public AttachFile findOne(Long fileId) {
        return fileRepository.findOne(fileId);
    }



}
