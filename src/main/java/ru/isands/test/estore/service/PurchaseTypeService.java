package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.PurchaseTypeDTO;
import ru.isands.test.estore.model.entity.directory.PurchaseType;

import java.util.List;

public interface PurchaseTypeService {

    Long createPurchaseType(PurchaseTypeDTO purchaseTypeDTO);

    void updatePurchaseType(PurchaseTypeDTO purchaseTypeDTO, Long id);

    void deletePurchaseType(Long id);

    PurchaseTypeDTO getPurchaseType(Long id);

    List<PurchaseType> getAllPurchaseTypes(int page, int size);
}
