package minyuk.board.repository;

import lombok.RequiredArgsConstructor;
import minyuk.board.domain.AttachFile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;

    public void save(AttachFile file) {
        em.persist(file);
    }

    public AttachFile findOne(Long id) {
        return em.find(AttachFile.class, id);
    }

    public List<AttachFile> findAll() {
        return em.createQuery("select af from AttachFile af", AttachFile.class).getResultList();
    }
}
