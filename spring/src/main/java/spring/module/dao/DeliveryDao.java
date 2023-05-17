package spring.module.dao;

import spring.module.model.Delivery;

import java.util.Collection;

public interface DeliveryDao extends Dao<Delivery> {
    Collection<Delivery> findByPhoneNumber(String phoneNumber);
}
