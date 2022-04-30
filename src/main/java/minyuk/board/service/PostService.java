package minyuk.board.service;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.AttachFile;
import minyuk.board.domain.FileInfo;
import minyuk.board.domain.Post;
import minyuk.board.domain.User;
import minyuk.board.repository.FileRepository;
import minyuk.board.repository.PostRepository;
import minyuk.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    //등록
    @Transactional
    public Long UploadPost(Long userId, String title, String contents, MultipartFile multipartFile) throws IOException {
        //엔티티 조회
        User user = userRepository.findOne(userId);

        //첨부파일 생성
        FileInfo fileInfo = fileService.storeFile(multipartFile);
        AttachFile attachFile = AttachFile.createAttachFile(fileInfo);

        //게시물 생성
        Post post = Post.createPost(user, title, contents, attachFile);

        //저장
        fileService.saveFile(attachFile);
        postRepository.save(post);

        return post.getId();
    }
}
