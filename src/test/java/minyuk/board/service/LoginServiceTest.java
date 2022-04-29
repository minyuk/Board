package minyuk.board.service;

import minyuk.board.domain.User;
import minyuk.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class LoginServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Test
    public void 로그인() throws Exception {
        //given
        User user = new User();
        user.setLoginId("kmh1234");
        user.setPassword("1234");

        //when
        Long userId = userService.join(user);
        User findUser = userRepository.findOne(userId);
        User loginUser1 = loginService.login("kmh12345", "1234");
        User loginUser2 = loginService.login("kmh1234", "1234");

        //then
        //로그인 실패 로직
        assertThat(loginUser1).isNull();
        //로그인 성공 로직
        assertThat(loginUser2).isNotNull();
        assertThat(loginUser2.getId()).isEqualTo(findUser.getId());
     }

}