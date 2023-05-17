package spring.module.dao.memory;

import spring.module.dao.DeliveryDao;
import spring.module.model.Delivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public class DeliveryDaoMemory implements DeliveryDao {

    private static final Map<Long, Delivery> DELIVERIES = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOG = LoggerFactory.getLogger(DeliveryDaoMemory.class);

    @Override
    public Delivery saveAndFlush(Delivery delivery) {
        if (delivery.getId() == null) {
            Long id = ID_GENERATOR.getAndIncrement();
            delivery.setId(id);
            LOG.info("Created delivery with the following id: " + id);
            DELIVERIES.put(id, delivery);
            return DELIVERIES.get(id);
        } else {
            LOG.info("Updating delivery with the following id: " + delivery.getId().toString());
            DELIVERIES.replace(delivery.getId(), delivery);
            return DELIVERIES.get(delivery.getId());
        }
    }

    @Override
    public void delete(Long id) {
        LOG.info("Deleting delivery with the following id: " + id.toString());
        DELIVERIES.remove(id);
    }

    @Override
    public Delivery getById(Long id) {
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
