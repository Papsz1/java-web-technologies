package spring.module.mapper;

import spring.module.model.Delivery;
import spring.module.dto.DeliveryInDto;
import spring.module.dto.DeliveryOutDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class DeliveryMapper {
    public abstract Delivery deliveryFromDto(DeliveryInDto deliveryDto);

    public abstract DeliveryOutDto dtoFromDelivery(Delivery delivery);

    @IterableMapping(elementTargetType = DeliveryOutDto.class)
    public abstract Collection<DeliveryOutDto> dtosFromDeliveries(Collection<Delivery> deliveries);

}
