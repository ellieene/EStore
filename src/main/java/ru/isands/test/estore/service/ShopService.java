package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ShopDTO;
import ru.isands.test.estore.model.entity.directory.Shop;

import java.util.List;

public interface ShopService {

    /**
     * Создание магазина
     *
     * @param shopDto магазин
     * @return ID магазина
     */
    Long createShop(ShopDTO shopDto);

    /**
     * Изменение магазина
     *
     * @param id      магазина
     * @param shopDto магазин
     */
    void updateShop(long id, ShopDTO shopDto);

    /**
     * Удаление магазина
     *
     * @param id магазина
     */
    void deleteShop(long id);

    /**
     * Получить один магазин
     *
     * @param id магазина
     * @return {@link ShopDTO}
     */
    ShopDTO getShop(long id);

    /**
     * Получить все магазины
     *
     * @return List {@link ShopDTO}
     */
    List<Shop> getAllShops(int page, int size);

    /**
     * Проверка магазина через сущность
     *
     * @param shop магазин
     * @return {@link Shop}
     */
    Shop shopCheckInDTO(ShopDTO shop);
}
