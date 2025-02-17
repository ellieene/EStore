package ru.isands.test.estore.model.entity;


import lombok.*;
import ru.isands.test.estore.model.entity.directory.PurchaseType;
import ru.isands.test.estore.model.entity.directory.Shop;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "electro_item_id", nullable = false)
    private ElectroItem electroItem;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private PurchaseType purchaseType;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
}
