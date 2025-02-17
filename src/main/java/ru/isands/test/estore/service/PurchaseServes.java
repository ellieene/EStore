package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.PurchaseDTO;
import ru.isands.test.estore.model.response.PurchaseResponse;

import java.util.List;

public interface PurchaseServes {

    Long createPurchase(PurchaseDTO purchaseDTO);

    void updatePurchase(PurchaseDTO purchaseDTO, Long id);

    void deletePurchase(Long id);

    PurchaseResponse getPurchase(Long id);

    List<PurchaseResponse> getAllPurchases(boolean sort, int page, int size);
}
