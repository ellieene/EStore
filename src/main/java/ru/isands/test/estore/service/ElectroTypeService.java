package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ElectroTypeDTO;
import ru.isands.test.estore.model.entity.directory.ElectroType;

import java.util.List;

public interface ElectroTypeService {

    /**
     * Создание типа товара
     *
     * @param typeDTO {@link ElectroTypeDTO}
     * @return ID типа товара
     */
    Long createType(ElectroTypeDTO typeDTO);

    /**
     * Изменение типа товара
     *
     * @param electroTypeDTO {@link ElectroTypeDTO}
     * @param id             ID типа товара
     */
    void updateType(ElectroTypeDTO electroTypeDTO, long id);

    /**
     * Удаление типа товаара
     *
     * @param id ID типа товара
     */
    void deleteType(long id);

    /**
     * Получить тип товара
     *
     * @param id ID типа товара
     * @return {@link ElectroTypeDTO}
     */
    ElectroTypeDTO getType(long id);

    /**
     * Получение всех типов товара
     *
     * @return List {@link ElectroType}
     */
    List<ElectroType> getAllTypes(int page, int size);
}
