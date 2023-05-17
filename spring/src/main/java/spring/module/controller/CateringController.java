package spring.module.controller;

import spring.module.dao.CateringDao;
import spring.module.dao.exception.RepositoryException;
import spring.module.model.Catering;
import spring.module.mapper.CateringMapper;
import spring.module.dto.CateringInDto;
import spring.module.dto.CateringOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/caterings")
public class CateringController {
    @Autowired
    private CateringDao cateringDao;
    @Autowired
    private CateringMapper cateringMapper;

    @GetMapping
    public Collection<CateringOutDto> findAll(@RequestParam(required = false) String number) {
        try {
            if (number == null) {
                return cateringMapper.dtosFromCaterings(cateringDao.findAll());
            }
            return cateringMapper.dtosFromCaterings(cateringDao.findByPhoneNumber(number));
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @PostMapping
    public void create(@RequestBody @Valid CateringInDto cateringDto) {
        try {
            cateringDao.saveAndFlush(cateringMapper.cateringFromDto(cateringDto));
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @GetMapping("/{id}")
    public CateringOutDto findById(@PathVariable("id") Long id) {
        try {
            Catering result = cateringDao.getById(id);
            if (result == null) {
                throw new NotFoundException("Error");
            }
            return cateringMapper.dtoFromCatering(result);
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            cateringDao.delete(id);
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }

    @PutMapping
    public void update(@RequestBody @Valid CateringInDto cateringDto) {
        try {
            cateringDao.saveAndFlush(cateringMapper.cateringFromDto(cateringDto));
        } catch (RepositoryException e) {
            throw new InternalErrorException("Error occured in the database");
        }
    }
}
