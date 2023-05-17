package spring.module.dao.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.module.model.Catering;
import spring.module.dao.CateringDao;

@Repository
@Profile("jpa")
public interface JpaCateringDao extends JpaRepository<Catering, Long>, CateringDao {
    @Modifying
    @Override
    @Query("delete from Catering where id = :id")
    void delete(Long id);
}
