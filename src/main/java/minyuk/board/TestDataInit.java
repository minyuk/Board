package minyuk.board;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.User;
import minyuk.board.service.PostService;
import minyuk.board.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final PostService postService;
    private final UserService userService;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        User user = new User();
        user.setLoginId("test1234");
        user.setPassword("test1!");
        user.setName("테스터");
        userService.join(user);

        User user2 = new User();
        user2.setLoginId("kmh1234");
        user2.setPassword("qwer1!");
        user2.setName("민혁");
        userService.join(user2);

        postService.uploadPost(user.getId(), "테스트", "테스트중입니다...");
        postService.uploadPost(user.getId(), "testing", "테스트중입니다...");
        postService.uploadPost(user2.getId(), "가나다라마바사아자차카타파하", "테스트중입니다...");
    }

}