package ru.isands.test.estore.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class ElectroShopId implements Serializable {
    private Long shop;
    private Long electroItem;

    public ElectroShopId() {
    }

    public ElectroShopId(Long shop, Long electroItem) {
        this.shop = shop;
        this.electroItem = electroItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectroShopId that = (ElectroShopId) o;
        return Objects.equals(shop, that.shop) && Objects.equals(electroItem, that.electroItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shop, electroItem);
    }
}
