package backend.module.dao.jdbc;

import backend.module.model.Catering;
import backend.module.dao.CateringDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcCateringDao implements CateringDao {
    private final DataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcCateringDao.class);

    public JdbcCateringDao() {
        dataSource = DataSourceFactory.getDataSource();
    }

    private void preparedStatement(PreparedStatement prep, Catering catering) throws SQLException {
        prep.setString(1, catering.getName());
        prep.setString(2, catering.getMenu());
        prep.setDouble(3, catering.getPrices());
        prep.setString(4, catering.getSales());
        prep.setString(5, catering.getPhoneNumber());
    }

    private Catering resultSet(ResultSet set) throws SQLException {
        return new Catering(
                set.getString("name"),
                set.getString("menu"),
                set.getDouble("prices"),
                set.getString("sales"),
                set.getString("phoneNumber")
        );
    }

    @Override
    public void create(Catering catering) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("insert into Caterings values(default, ?, ?, ?, ?, ?)");
            preparedStatement(prep, catering);
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
        }
    }

    @Override
    public void update(Long id, String menuContent) {
        LOG.info("UPDATE MENU - ID: " + id + "Menu content: " + menuContent);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("update Caterings set menu = ? where id = ?");
            prep.setString(1, menuContent);
            prep.setLong(2, id);
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error: {}", e.toString());
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
        }
    }

    @Override
    public Catering findById(Long id) {
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
        }
        return caterings;
    }
}
