package backend.module.dao.memory;

import backend.module.model.Delivery;
import backend.module.dao.DeliveryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DeliveryDaoMemory implements DeliveryDao {

    private static final Map<Long, Delivery> DELIVERIES = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOG = LoggerFactory.getLogger(DeliveryDaoMemory.class);

    @Override
    public void create(Delivery delivery) {
        Long id = ID_GENERATOR.getAndIncrement();
        delivery.setId(id);
        LOG.info("Created delivery with the following id: " + id);
        DELIVERIES.put(id, delivery);
    }

    @Override
    public void update(Long id, String Address) {
        LOG.info("Updating delivery with the following id: " + id.toString() + " Address: " + Address);
        Delivery delivery = findById(id);
        delivery.setAddress(Address);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Deleting delivery with the following id: " + id.toString());
        DELIVERIES.remove(id);
    }

    @Override
    public Delivery findById(Long id) {
        LOG.info("Searching for id: " + id.toString());
        return DELIVERIES.get(id);
    }

    @Override
    public Collection findAll() {
        LOG.info("Searching for all deliveries");
        return DELIVERIES.values();
    }

    @Override
    public Collection<Delivery> findByPhoneNumber(String phoneNumber) {
        Collection<Delivery> phoneNumberDeliveries = new ArrayList<>();
        Iterator<Delivery> iterator = DELIVERIES.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPhoneNumber().equals(phoneNumber)) {
                phoneNumberDeliveries.add(iterator.next());
            }
        }
        return phoneNumberDeliveries;
    }
}
