package spring.module.dao;

import spring.module.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    T saveAndFlush(T entity);

    void delete(Long id);

    T getById(Long id);

    Collection<T> findAll();
}
