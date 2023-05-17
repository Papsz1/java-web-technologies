package spring.module.dao.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.module.model.Delivery;
import spring.module.dao.DeliveryDao;

@Repository
@Profile("jpa")
public interface JpaDeliveryDao extends JpaRepository<Delivery, Long>, DeliveryDao {
    @Override
    @Modifying
    @Query("delete from Delivery where id = :id")
    void delete(Long id);
}
