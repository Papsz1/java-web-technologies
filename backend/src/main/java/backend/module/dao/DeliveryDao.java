package backend.module.dao;

import backend.module.model.Delivery;

import java.util.Collection;

public interface DeliveryDao extends Dao<Delivery> {
    Collection<Delivery> findByPhoneNumber(String phoneNumber);

}
