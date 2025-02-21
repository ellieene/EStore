package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ShopDTO;
import ru.isands.test.estore.model.entity.directory.Shop;

import java.util.List;

public interface ShopService {

    Long createShop(ShopDTO shopDto);

    void updateShop(long id, ShopDTO shopDto);

    void deleteShop(long id);

    ShopDTO getShop(long id);

    List<Shop> getAllShops(int page, int size);

    Shop shopCheckInDTO(ShopDTO shop);
}
