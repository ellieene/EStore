package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityExist;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.PurchaseTypeDTO;
import ru.isands.test.estore.model.entity.directory.PurchaseType;
import ru.isands.test.estore.repository.PurchaseTypeRepository;
import ru.isands.test.estore.service.PurchaseTypeService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseTypeServiceImpl implements PurchaseTypeService {

    private final ModelMapper modelMapper;
    private final PurchaseTypeRepository purchaseTypeRepository;

    /**
     * Создание тип оплаты
     *
     * @param purchaseTypeDTO DTO типа оплаты
     * @return ID типа оплаты
     */
    @Transactional
    @Override
    public Long createPurchaseType(PurchaseTypeDTO purchaseTypeDTO) {
        purchaseTypeRepository.findByName(purchaseTypeDTO.getName())
                .ifPresent((e) -> {
                    throw new EntityExist("Тип оплаты '" + purchaseTypeDTO.getName() + "' уже есть");
                });
        PurchaseType purchaseType = modelMapper.map(purchaseTypeDTO, PurchaseType.class);
        return purchaseTypeRepository.save(purchaseType).getId();
    }

    /**
     * Изменение типа оплаты
     *
     * @param purchaseTypeDTO DTO типа оплаты
     * @param id              ID типа оплаты
     */
    @Transactional
    @Override
    public void updatePurchaseType(PurchaseTypeDTO purchaseTypeDTO, long id) {
        purchaseTypeRepository.findByName(purchaseTypeDTO.getName())
                .ifPresent((e) -> {
                    throw new EntityExist("Такой тип оплаты уже есть");
                });

        PurchaseType purchaseType = purchaseTypeCheck(id);
        modelMapper.map(purchaseTypeDTO, purchaseType);
        purchaseTypeRepository.save(purchaseType);
    }

    /**
     * Удаление типа оплаты
     *
     * @param id ID типа оплаты
     */
    @Transactional
    @Override
    public void deletePurchaseType(long id) {
        purchaseTypeRepository.delete(purchaseTypeCheck(id));
    }

    /**
     * Получение типа оплаты
     *
     * @param id Id типа оплаты
     * @return {@link PurchaseTypeDTO}
     */
    @Transactional
    @Override
    public PurchaseTypeDTO getPurchaseType(long id) {
        PurchaseType purchaseType = purchaseTypeCheck(id);

        return modelMapper.map(purchaseType, PurchaseTypeDTO.class);
    }

    /**
     * Получение всех типов оплаты
     *
     * @return List {@link PurchaseType}
     */
    @Transactional
    @Override
    public List<PurchaseType> getAllPurchaseTypes(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return purchaseTypeRepository.findAll(pageRequest).getContent();
    }

    /**
     * Проверка типа опланы по полю name
     *
     * @param name название типа оплаты
     * @return {@link PurchaseType}
     */
    public PurchaseType purchaseTypeCheck(String name) {
        return purchaseTypeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFound("Тип транзакции не найден"));
    }

    /**
     * Проверка типа оплаты по ID
     *
     * @param id ID типа оплаты
     * @return {@link PurchaseType}
     */
    private PurchaseType purchaseTypeCheck(long id) {
        return purchaseTypeRepository
                .findById(id).orElseThrow(() -> new EntityNotFound("Такой тип оплаты не найден"));
    }

}
