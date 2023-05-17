package spring.module.dao.memory;

import spring.module.dao.CateringDao;
import spring.module.model.Catering;
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
public class CateringDaoMemory implements CateringDao {

    private static final Map<Long, Catering> MENUS = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOG = LoggerFactory.getLogger(CateringDaoMemory.class);

    @Override
    public Catering getById(Long id) {
        LOG.info("Searching for id: " + id.toString());
        return MENUS.get(id);
    }

    @Override
    public Catering saveAndFlush(Catering catering) {
        if (catering.getId() == null) { // in this case we create
            Long id = ID_GENERATOR.getAndIncrement();
            catering.setId(id);
            LOG.info("Created menu with the following id: " + id);
            MENUS.put(id, catering);
            return MENUS.get(id);
        } else {
            LOG.info("Updating menu with the following id: ");
            MENUS.replace(catering.getId(), catering);
            return MENUS.get(catering.getId());
        }
    }

    @Override
    public void delete(Long id) {
        LOG.info("Deleting menu with the following id: " + id.toString());
        MENUS.remove(id);
    }

    @Override
    public Collection<Catering> findAll() {
        LOG.info("Searching for all menus");
        return MENUS.values();
    }

    @Override
    public Collection<Catering> findByPhoneNumber(String phoneNumber) {
        Collection<Catering> phoneNumberMenus = new ArrayList<>();
        Iterator<Catering> iterator = MENUS.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPhoneNumber().equals(phoneNumber)) {
                phoneNumberMenus.add(iterator.next());
            }
        }
        return phoneNumberMenus;
    }
}
