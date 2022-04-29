package minyuk.board.service;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.User;
import minyuk.board.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /**
     * @return null 로그인 실패
     */

    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
}
