package spring.module.dao.jdbc;

import spring.module.dao.CateringDao;
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

@Profile("jdbc")
@Repository
public class JdbcCateringDao implements CateringDao {
    @Autowired
    private DataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcDeliveryDao.class);

    private void preparedStatement(PreparedStatement prep, Catering catering) throws SQLException {
        prep.setString(1, catering.getName());
        prep.setString(2, catering.getMenu());
        prep.setDouble(3, catering.getPrices());
        prep.setString(4, catering.getSales());
        prep.setString(5, catering.getPhoneNumber());
    }

    private Catering resultSet(ResultSet set) throws SQLException {
        Catering catering = new Catering(
                set.getString("name"),
                set.getString("menu"),
                set.getDouble("prices"),
                set.getString("sales"),
                set.getString("phoneNumber"),
                (Collection<Delivery>) set.getObject("deliveries")
        );
        catering.setId(set.getLong("id"));
        return catering;
    }

    @Override
    public Catering saveAndFlush(Catering catering) {
        long id = -5;
        try (Connection connection = dataSource.getConnection()) {
            if (catering.getId() == null) { // if there is no id we create one
                PreparedStatement prep = connection
                        .prepareStatement("insert into Caterings values(default, ?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                preparedStatement(prep, catering);
                prep.executeUpdate();

                ResultSet dbValues = prep.getGeneratedKeys();
                if (dbValues.next()) {
                    id = dbValues.getLong(1);
                }
                return getById(id);
            } else {
                PreparedStatement prep = connection
                        .prepareStatement("update Caterings set name = ?, menu = ?, prices = ?, sales = ?,"
                                + " phoneNumber = ? where id = ?");
                preparedStatement(prep, catering);
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
        LOG.info("DELETE MENU - ID: " + id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("delete from Caterings where id = ?");
            prep.setLong(1, id);
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }
    }

    @Override
    public Catering getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from Caterings where id = ?");
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
    public Collection<Catering> findAll() {
        Collection<Catering> caterings = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from Caterings");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                caterings.add(resultSet(set));
            }
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }
        return caterings;
    }

    @Override
    public Collection<Catering> findByPhoneNumber(String phoneNumber) {
        Collection<Catering> caterings = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from Caterings where phoneNumber = ?");
            prep.setString(1, phoneNumber);
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                caterings.add(resultSet(set));
            }
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
            throw new RepositoryException();
        }
        return caterings;
    }
}
