package spring.module.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "deliveries")

public class Delivery extends BaseEntity {
    private String address;
    private Integer menu;
    private Double prices;
    private String date;
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catering_id", nullable = false)
    private Catering catering;
}
