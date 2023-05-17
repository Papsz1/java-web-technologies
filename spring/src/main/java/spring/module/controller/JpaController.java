package spring.module.controller;

import spring.module.dao.CateringDao;
import spring.module.dao.exception.RepositoryException;
import spring.module.dto.DeliveryInDto;
import spring.module.model.Catering;
import spring.module.model.Delivery;
import spring.module.mapper.DeliveryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/caterings/{id}/deliveries")
public class JpaController {
    @Autowired
    private CateringDao cateringDao;
    @Autowired
    private DeliveryMapper deliveryMapper;

    @GetMapping
    public Collection<Delivery> findAll(@PathVariable("id") Long id) {
        try {
            Catering catering = cateringDao.getById(id);
            if (catering == null) {
                throw new NotFoundException("Error");
            }
            return catering.getDeliveries();
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @PostMapping
    public void create(@PathVariable("id") Long id, @RequestBody @Valid DeliveryInDto deliveryInDto) {
        try {
            Catering catering = cateringDao.getById(id);
            if (catering == null) {
                throw new NotFoundException("Error");
            }
            catering.getDeliveries().add(deliveryMapper.deliveryFromDto(deliveryInDto));
            cateringDao.saveAndFlush(catering);
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @DeleteMapping("/{did}")
    public void delete(@PathVariable("id") Long id, @PathVariable("did") Long did) {
        try {
            Catering catering = cateringDao.getById(id);
            Collection<Delivery> deliveries = catering.getDeliveries();
            for (Delivery delivery : deliveries) {
                if (Objects.equals(delivery.getId(), did)) {
                    catering.getDeliveries().remove(delivery);
                    break;
                }
            }
            cateringDao.saveAndFlush(catering);
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }
}
