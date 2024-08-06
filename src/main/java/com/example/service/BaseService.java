package com.example.service;

import com.example.entity.BaseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public abstract class BaseService<E extends BaseEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    public abstract Class<E> getClassType();

    public E getById(int id) {
        return entityManager.find(getClassType(), id);
    }

    public List<E> findAll() {
        String query = "FROM " + getClassType().getSimpleName() + " e ORDER BY e.id";
        return entityManager.createQuery(query, getClassType()).getResultList();
    }

    @Transactional
    public List<E> executeNativeQuery(String sql) {
        try {
            Query query = entityManager.createNativeQuery(sql, getClassType());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<E> executeNativeQuery(String sql, Map<String, Object> parameters) {
        Query query = entityManager.createNativeQuery(sql, getClassType());

        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return query.getResultList();
    }

    @Transactional
    public E saveOrUpdate(E entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            entityManager.persist(entity);
            return entity;
        } else {
            //update entity
            return entityManager.merge(entity);
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        String query = "DELETE FROM " + getClassType().getSimpleName() + " e WHERE e.id = :id";
        return entityManager.createQuery(query).setParameter("id", id).executeUpdate() > 0;
    }
}
