package backend.module.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class Catering extends BaseEntity {
    private String name;
    private String menu;
    private Double prices;
    private String sales;
    private String phoneNumber;

    public Catering(String name, String menu, Double prices, String sales, String phoneNumber) {
        super();
        this.name = name;
        this.menu = menu;
        this.prices = prices;
        this.sales = sales;
        this.phoneNumber = phoneNumber;
    }
}
