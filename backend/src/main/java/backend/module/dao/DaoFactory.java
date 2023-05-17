package backend.module.dao;

import backend.module.Config;
import backend.module.ConfigFactory;
import backend.module.dao.jdbc.JdbcDaoFactory;
import backend.module.dao.memory.DaoMemoryFactory;

public abstract class DaoFactory {
    private static DaoFactory instance;

    public abstract CateringDao getCateringDao();

    public abstract DeliveryDao getDeliveryDao();

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Config config = ConfigFactory.getConfig();

            if ("jdbc".equals(config.getDaoType())) {
                instance = new JdbcDaoFactory();
            } else {
                instance = new DaoMemoryFactory();
            }
        }
        return instance;
    }
}

