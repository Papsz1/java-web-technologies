package backend.module.dao.jdbc;

import backend.module.model.Delivery;
import backend.module.dao.DeliveryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;
import java.util.ArrayList;

public class JdbcDeliveryDao implements DeliveryDao {

    private final DataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcDeliveryDao.class);

    public JdbcDeliveryDao() {
        dataSource = DataSourceFactory.getDataSource();
    }

    private void preparedStatement(PreparedStatement prep, Delivery delivery) throws SQLException {
        prep.setString(1, delivery.getAddress());
        prep.setInt(2, delivery.getMenu());
        prep.setDouble(3, delivery.getPrices());
        prep.setString(4, delivery.getDate());
        prep.setString(5, delivery.getPhoneNumber());
    }

    private Delivery resultSet(ResultSet set) throws SQLException {
        return new Delivery(
                set.getString("address"),
                set.getInt("menu"),
                set.getDouble("price"),
                set.getString("date"),
                set.getString("phoneNumber")
        );
    }

    @Override
    public Delivery findById(Long id) {
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
        }

        return null;
    }

    @Override
    public void create(Delivery delivery) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("insert into Deliveries values(default, ?, ?, ?, ?, ?)");
            preparedStatement(prep, delivery);
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
        }
    }

    @Override
    public void update(Long id, String Address) {
        LOG.info("UPDATE DELIVERY - ID: " + id + "Address: " + Address);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("update Deliveries set Address = ? where id = ?");
            prep.setString(1, Address);
            prep.setLong(2, id);
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
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
        }
        return deliveries;
    }
}
