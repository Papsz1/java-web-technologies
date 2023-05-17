package spring.module.dao;

import spring.module.model.Catering;

import java.util.Collection;

public interface CateringDao extends Dao<Catering> {
    Collection<Catering> findByPhoneNumber(String phoneNumber);
}
