package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ElectroItemDTO;
import ru.isands.test.estore.model.entity.ElectroItem;

import java.util.List;

public interface ElectroItemService {

    /**
     * Создание нового товара
     *
     * @param electroItemDTO DTO товара
     * @return ID нового товара
     */
    Long addElectroItem(ElectroItemDTO electroItemDTO);

    /**
     * Изменение товара
     *
     * @param electroItemDTO {@link ElectroItemDTO}
     * @param id             ID товара
     */
    void updateElectroItem(ElectroItemDTO electroItemDTO, long id);

    /**
     * Получение товара
     *
     * @param id ID товара
     * @return {@link ElectroItemDTO}
     */
    ElectroItemDTO getElectroItem(long id);

    /**
     * Получение всех товаров
     *
     * @return List {@link ElectroItemDTO}
     */
    List<ElectroItemDTO> getElectroItemAll(int page, int size);

    /**
     * Удаление товара
     *
     * @param id ID товара
     */
    void deleteElectroItem(long id);

    /**
     * Проверка товара через поле name
     *
     * @param name Название товара
     * @return {@link ElectroItem}
     */
    ElectroItem electroItemCheck(String name);
}
