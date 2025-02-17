package ru.isands.test.estore.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.isands.test.estore.model.entity.directory.Shop;

import javax.persistence.*;


@Entity
@Table(name = "electro_shop")
@IdClass(ElectroShopId.class)
public class ElectroShop {

    @Id
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @Getter
    @Setter
    private Shop shop;

    @Id
    @ManyToOne
    @JoinColumn(name = "electro_item_id", nullable = false)
    @Getter
    @Setter
    private ElectroItem electroItem;

    @Column(name = "count", nullable = false)
    @Setter
    private int count;

    public int getCount() {
        return count;
    }

    @PrePersist
    @PreUpdate
    public void updateElectroItemCount() {
        electroItem.updateTotalCount();
    }
}
