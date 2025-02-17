package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ShopDTO;
import ru.isands.test.estore.model.entity.directory.Shop;

import java.util.List;

public interface ShopService {

    Long createShop(ShopDTO shopDto);

    void updateShop(Long id, ShopDTO shopDto);

    void deleteShop(Long id);

    ShopDTO getShop(Long id);

    List<Shop> getAllShops(int page, int size);

    Shop shopCheckInDTO(ShopDTO shop);
}
