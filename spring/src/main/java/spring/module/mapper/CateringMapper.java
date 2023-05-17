package spring.module.mapper;

import spring.module.model.Catering;
import spring.module.dto.CateringInDto;
import spring.module.dto.CateringOutDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CateringMapper {
    public abstract Catering cateringFromDto(CateringInDto cateringDto);

    public abstract CateringOutDto dtoFromCatering(Catering catering);

    @IterableMapping(elementTargetType = CateringOutDto.class)
    public abstract Collection<CateringOutDto> dtosFromCaterings(Collection<Catering> caterings);

}
