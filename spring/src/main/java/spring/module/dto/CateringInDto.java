package spring.module.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CateringInDto {
    @NotNull
    @Length(max = 50)
    private String name;
    @NotNull
    @Length(max = 50)
    private String menu;
    @NotNull
    @Positive
    private Double prices;
    @NotNull
    @Length(max = 50)
    private String sales;
    @NotNull
    @Length(max = 50)
    private String phoneNumber;
}
