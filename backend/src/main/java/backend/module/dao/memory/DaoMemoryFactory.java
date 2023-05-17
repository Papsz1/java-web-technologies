package backend.module.dao.memory;

import backend.module.model.Catering;
import backend.module.model.Delivery;
import backend.module.dao.DaoFactory;
import backend.module.dao.DeliveryDao;

public class DaoMemoryFactory extends DaoFactory {
    private static CateringDaoMemory cateringDao;
    private static DeliveryDaoMemory deliveryDao;

    @Override
    public synchronized CateringDaoMemory getCateringDao() {
        if (cateringDao == null) {
            cateringDao = new CateringDaoMemory();
            Catering catering = new Catering("Menu", "k2",
                    1.0, "k2", "071234568");
            cateringDao.create(catering);
        }
        return cateringDao;
    }

    @Override
    public synchronized DeliveryDao getDeliveryDao() {
        if (deliveryDao == null) {
            deliveryDao = new DeliveryDaoMemory();
            Delivery delivery = new Delivery("First street", 0,
                    1.0, "2023.01.01", "071234568");
            deliveryDao.create(delivery);
        }
        return deliveryDao;
    }
}
