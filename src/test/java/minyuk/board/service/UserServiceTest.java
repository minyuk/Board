package minyuk.board.service;

import minyuk.board.domain.User;
import minyuk.board.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void 회원가입() throws Exception {
        //given
        User user = new User();
        user.setLoginId("kmh1234");
        user.setPassword("1234");
        user.setName("UserA");

        //when
        Long userId = userService.join(user);

        //then
        assertEquals(user, userRepository.findOne(userId));
    }


}