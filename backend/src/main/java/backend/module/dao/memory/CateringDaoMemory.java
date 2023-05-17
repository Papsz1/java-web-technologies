package backend.module.dao.memory;

import backend.module.model.Catering;
import backend.module.dao.CateringDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CateringDaoMemory implements CateringDao {

    private static final Map<Long, Catering> MENUS = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOG = LoggerFactory.getLogger(CateringDaoMemory.class);

    @Override
    public Catering findById(Long id) {
        LOG.info("Searching for ID: " + id.toString());
        return MENUS.get(id);
    }

    @Override
    public void create(Catering catering) {
        Long id = ID_GENERATOR.getAndIncrement();
        catering.setId(id);
        LOG.info("Created menu with the following id: " + id);
        MENUS.put(id, catering);
    }

    @Override
    public void update(Long id, String menuContent) {
        LOG.info("Updating menu with the following id: " + id.toString() + " menuContent: " + menuContent);
        Catering menu = findById(id);
        menu.setMenu(menuContent);
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

