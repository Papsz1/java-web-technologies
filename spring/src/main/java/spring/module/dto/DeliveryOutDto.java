package spring.module.dto;

import lombok.Data;

@Data
public class DeliveryOutDto {
    Long id;
    private String address;
    private Integer menu;
    private Double prices;
    private String date;
    private String phoneNumber;
}
