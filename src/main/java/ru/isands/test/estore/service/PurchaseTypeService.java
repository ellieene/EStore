package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.PurchaseTypeDTO;
import ru.isands.test.estore.model.entity.directory.PurchaseType;

import java.util.List;

public interface PurchaseTypeService {

    /**
     * Создание тип оплаты
     *
     * @param purchaseTypeDTO DTO типа оплаты
     * @return ID типа оплаты
     */
    Long createPurchaseType(PurchaseTypeDTO purchaseTypeDTO);

    /**
     * Изменение типа оплаты
     *
     * @param purchaseTypeDTO DTO типа оплаты
     * @param id              ID типа оплаты
     */
    void updatePurchaseType(PurchaseTypeDTO purchaseTypeDTO, long id);

    /**
     * Удаление типа оплаты
     *
     * @param id ID типа оплаты
     */
    void deletePurchaseType(long id);

    /**
     * Получение типа оплаты
     *
     * @param id Id типа оплаты
     * @return {@link PurchaseTypeDTO}
     */
    PurchaseTypeDTO getPurchaseType(long id);

    /**
     * Получение всех типов оплаты
     *
     * @return List {@link PurchaseType}
     */
    List<PurchaseType> getAllPurchaseTypes(int page, int size);
}
