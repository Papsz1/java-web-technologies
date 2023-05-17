package backend.module.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class Delivery extends BaseEntity {
    private String address;
    private Integer menu;
    private Double prices;
    private String date;
    private String phoneNumber;

    public Delivery(String address, Integer menu, Double prices, String date, String phoneNumber) {
        super();
        this.address = address;
        this.menu = menu;
        this.prices = prices;
        this.date = date;
        this.phoneNumber = phoneNumber;
    }
}
