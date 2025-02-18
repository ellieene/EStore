package ru.isands.test.estore.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.isands.test.estore.model.entity.directory.Shop;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "electro_shop")
@IdClass(ElectroShopId.class)
public class ElectroShop {

    @Id
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Id
    @ManyToOne
    @JoinColumn(name = "electro_item_id", nullable = false)
    private ElectroItem electroItem;

    @Column(name = "count", nullable = false)
    private int count;

    @PrePersist
    @PreUpdate
    public void updateElectroItemCount() {
        electroItem.updateTotalCount();
    }
}
