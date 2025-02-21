package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.PurchaseTypeDTO;
import ru.isands.test.estore.model.entity.directory.PurchaseType;

import java.util.List;

public interface PurchaseTypeService {

    Long createPurchaseType(PurchaseTypeDTO purchaseTypeDTO);

    void updatePurchaseType(PurchaseTypeDTO purchaseTypeDTO, long id);

    void deletePurchaseType(long id);

    PurchaseTypeDTO getPurchaseType(long id);

    List<PurchaseType> getAllPurchaseTypes(int page, int size);
}
