package spring.module.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "caterings")

public class Catering extends BaseEntity {
    private String name;
    private String menu;
    private Double prices;
    private String sales;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Collection<Delivery> deliveries;
}
