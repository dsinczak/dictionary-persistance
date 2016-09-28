package org.dsinczak.domain.dict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class DictionaryRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(DictionaryRepository.class);

    public DictionaryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    private EntityManager entityManager;

    public <T extends Dictionary> T save(T entity) {
        LOGGER.info("Saving dictionary entity {}", entity);
        return entityManager.merge(entity);
    }

    public <T extends Dictionary> T find(String code, Class<T> clazz) {
        return entityManager.createQuery("SELECT d FROM " + clazz.getName() + " d WHERE d.code = :code", clazz)
                .setParameter("code", code)
                .getSingleResult();
    }

    public <T extends Dictionary> List<T> findAll(Class<T> clazz) {
        return entityManager.createQuery("SELECT d FROM " + clazz.getName() + " d", clazz).getResultList();
    }

    public <T extends Dictionary> void delete(String code, Class<T> clazz) {
        entityManager.createQuery("DELETE FROM " + clazz.getName() + " d WHERE d.code = :code")
                .setParameter("code", code)
                .executeUpdate();
    }

    public <T extends Dictionary> void deleteAll(Class<T> clazz) {
        entityManager.createQuery("DELETE FROM " + clazz.getName())
                .executeUpdate();
    }


}
