package spring.module.dto;

import lombok.Data;

@Data
public class CateringOutDto {
    Long id;
    private String name;
    private String menu;
    private Double prices;
    private String sales;
    private String phoneNumber;
}
