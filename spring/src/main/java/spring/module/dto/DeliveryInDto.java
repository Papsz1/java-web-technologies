package spring.module.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class DeliveryInDto {
    @NotNull
    @Length(max = 50)
    private String address;
    @NotNull
    @Positive
    private Integer menu;
    @NotNull
    @Positive
    private Double prices;
    @NotNull
    @Length(max = 50)
    private String date;
    @NotNull
    @Length(max = 50)
    private String phoneNumber;
}
