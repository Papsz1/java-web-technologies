package spring.module.controller;

import spring.module.dao.DeliveryDao;
import spring.module.dao.exception.RepositoryException;
import spring.module.model.Delivery;
import spring.module.mapper.DeliveryMapper;
import spring.module.dto.DeliveryInDto;
import spring.module.dto.DeliveryOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    @Autowired
    private DeliveryDao deliveryDao;
    @Autowired
    private DeliveryMapper deliveryMapper;

    @GetMapping
    public Collection<DeliveryOutDto> findAll(@RequestParam(required = false) String number) {
        try {
            if (number == null) {
                return deliveryMapper.dtosFromDeliveries(deliveryDao.findAll());
            }
            return deliveryMapper.dtosFromDeliveries(deliveryDao.findByPhoneNumber(number));
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @PostMapping
    @ResponseBody
    public String create(@RequestBody @Valid DeliveryInDto deliveryDto) {
        try {
            deliveryDao.saveAndFlush(deliveryMapper.deliveryFromDto(deliveryDto));
            return "Entity created";
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @GetMapping("/{id}")
    public DeliveryOutDto findById(@PathVariable("id") Long id) {
        try {
            Delivery result = deliveryDao.getById(id);
            if (result == null) {
                throw new NotFoundException("Error");
            }
            return deliveryMapper.dtoFromDelivery(result);
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            deliveryDao.delete(id);
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String update(@RequestBody @Valid DeliveryInDto deliveryDto) {
        try {
            deliveryDao.saveAndFlush(deliveryMapper.deliveryFromDto(deliveryDto));
            return "Entity updated";
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

}
