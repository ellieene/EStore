package ru.isands.test.estore.service;

import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.model.request.PurchasesDropRequest;
import ru.isands.test.estore.model.response.ElectroItemResponce;

import java.util.List;

public interface ElectroShopService {

    /**
     * Кокой и какое кол-во товара в магазине
     *
     * @param id ID магазина
     * @return List {@link ElectroItemResponce}
     */
    List<ElectroItemResponce> getItemInShop(Long id, int page, int size);

    /**
     * Заказ товара в магазин
     *
     * @param purchasesDropRequest DTO заказа
     */
    void setCountTest(PurchasesDropRequest purchasesDropRequest);

    /**
     * Покупка товара
     *
     * @param electroItemId ID товара
     * @param shop          Магазин
     * @param count         Кол-во
     */
    void setCount(ElectroItem electroItemId, Shop shop, int count);
}
