package ru.isands.test.estore.service;

import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.model.request.PurchasesDropRequest;
import ru.isands.test.estore.model.response.ElectroItemResponce;

import java.util.List;

public interface ElectroShopService {
    List<ElectroItemResponce> getItemInShop(Long id, int page, int size);

    void setCountTest(PurchasesDropRequest purchasesDropRequest);

    void setCount(ElectroItem electroItemId, Shop shop, int count);
}
