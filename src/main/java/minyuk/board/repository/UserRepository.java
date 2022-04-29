package minyuk.board.repository;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    //  1. @Autowired <- @PersistenceContext spring boot 가 지원.
    //  2. 생성자(@RequiredArgsConstructor 가 생성) 1개인 경우 @Autowired 생략가능
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByName(String name) {
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<User> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(u -> u.getLoginId().equals(loginId))
                .findFirst();
    }

//    public List<User> findByLoginId(String loginId) {
//        return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
//                .setParameter("loginId", loginId)
//                .getResultList();
//    }
}
