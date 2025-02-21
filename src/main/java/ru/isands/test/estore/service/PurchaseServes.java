package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.PurchaseDTO;
import ru.isands.test.estore.model.response.PurchaseResponse;

import java.util.List;

public interface PurchaseServes {

    /**
     * Создание покупки
     *
     * @param purchaseDTO DTO покупки
     * @return ID покупки
     */
    Long createPurchase(PurchaseDTO purchaseDTO);

    /**
     * Изменение чека покупки
     *
     * @param purchaseDTO DTO чека
     * @param id          ID чека
     */
    void updatePurchase(PurchaseDTO purchaseDTO, long id);

    /**
     * Удаление чека
     *
     * @param id ID чека
     */
    void deletePurchase(long id);

    /**
     * Получение чека по ID
     *
     * @param id ID чека
     * @return {@link PurchaseDTO} чек
     */
    PurchaseResponse getPurchase(long id);

    /**
     * Получение всех чеков
     *
     * @return List {@link PurchaseResponse} чеки
     */
    List<PurchaseResponse> getAllPurchases(boolean sort, int page, int size);
}
