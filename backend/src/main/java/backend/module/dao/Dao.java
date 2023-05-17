package backend.module.dao;

import backend.module.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    void create(T entity);

    void update(Long id, String Address);

    void delete(Long id);

    T findById(Long id);

    Collection<T> findAll();
}

