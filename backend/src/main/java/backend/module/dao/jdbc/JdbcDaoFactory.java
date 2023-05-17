package backend.module.dao.jdbc;

import backend.module.dao.DaoFactory;
import backend.module.dao.CateringDao;
import backend.module.dao.DeliveryDao;

public class JdbcDaoFactory extends DaoFactory {
    private static JdbcCateringDao dao;
    private static JdbcDeliveryDao dao2;

    @Override
    public synchronized CateringDao getCateringDao() {
        if (dao == null) {
            dao = new JdbcCateringDao();
        }
        return dao;
    }

    @Override
    public synchronized DeliveryDao getDeliveryDao() {
        if (dao2 == null) {
            dao2 = new JdbcDeliveryDao();
        }
        return dao2;
    }
}

