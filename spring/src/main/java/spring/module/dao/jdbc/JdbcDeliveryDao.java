package spring.module.dao.jdbc;

import spring.module.dao.DeliveryDao;
import spring.module.dao.exception.RepositoryException;
import spring.module.model.Catering;
import spring.module.model.Delivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@Profile("jdbc")
public class JdbcDeliveryDao implements DeliveryDao {
    @Autowired
    private DataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcDeliveryDao.class);

    private void preparedStatement(PreparedStatement prep, Delivery delivery) throws SQLException {
        prep.setString(1, delivery.getAddress());
        prep.setInt(2, delivery.getMenu());
        prep.setDouble(3, delivery.getPrices());
        prep.setString(4, delivery.getDate());
        prep.setString(5, delivery.getPhoneNumber());
        prep.setLong(6, delivery.getCatering().getId());
    }

    private Delivery resultSet(ResultSet set) throws SQLException {
        Delivery delivery = new Delivery(
                set.getString("address"),
                set.getInt("menu"),
                set.getDouble("price"),
                set.getString("date"),
                set.getString("phoneNumber"),
                set.getObject("catering_id", Catering.class)
        );
        delivery.setId(set.getLong("id"));
        return delivery;
    }

    @Override
    public Delivery getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from Deliveries where id = ?");
            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return resultSet(set);
            }
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }

        return null;
    }

    @Override
    public Delivery saveAndFlush(Delivery delivery) {
        long id = -5;
        try (Connection connection = dataSource.getConnection()) {
            if (delivery.getId() == null) { // if there is no id we create one
                PreparedStatement prep = connection
                        .prepareStatement("insert into Deliveries values(default, ?, ?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                preparedStatement(prep, delivery);
                prep.executeUpdate();

                ResultSet dbValues = prep.getGeneratedKeys();
                if (dbValues.next()) {
                    id = dbValues.getLong(1);
                }
                return getById(id);
            } else {        // if twe have id we are only going to update
                PreparedStatement prep = connection
                        .prepareStatement("update Deliveries set address = ?, menu = ?, price = ?, date = ?, "
                                + "phoneNumber = ? where id = ?");
                preparedStatement(prep, delivery);
                prep.setLong(6, id);
                prep.executeUpdate();
                return getById(id);
            }

        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }
    }

    @Override
    public void delete(Long id) {
        LOG.info("DELETE DELIVERY - ID: " + id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("delete from Deliveries where id = ?");
            prep.setLong(1, id);
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }
    }

    @Override
    public Collection<Delivery> findAll() {
        Collection<Delivery> deliveries = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from Deliveries");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                deliveries.add(resultSet(set));
            }
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }
        return deliveries;
    }

    @Override
    public Collection<Delivery> findByPhoneNumber(String phoneNumber) {
        Collection<Delivery> deliveries = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from Deliveries where phoneNumber = ?");
            prep.setString(1, phoneNumber);
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                deliveries.add(resultSet(set));
            }
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }
        return deliveries;
    }
}
