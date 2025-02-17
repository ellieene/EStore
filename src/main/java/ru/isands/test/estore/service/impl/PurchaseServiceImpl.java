package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.PurchaseDTO;
import ru.isands.test.estore.model.entity.Purchase;
import ru.isands.test.estore.model.response.PurchaseResponse;
import ru.isands.test.estore.repository.PurchaseRepository;
import ru.isands.test.estore.service.PurchaseServes;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseServes {

    private final PurchaseRepository purchaseRepository;

    private final ModelMapper modelMapper;

    private final ShopServiceImpl shopService;
    private final PurchaseTypeServiceImpl purchaseTypeService;
    private final EmployeeServiceImpl employeeService;
    private final ElectroItemServiceImpl electroItemService;
    private final ElectroShopServiceImpl electroShopService;

    /**
     * Создание покупки
     *
     * @param purchaseDTO DTO покупки
     * @return ID покупки
     */
    @Transactional
    @Override
    public Long createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = Purchase
                .builder()
                .shop(shopService.shopCheckInDTO(purchaseDTO.getShop()))
                .purchaseType(purchaseTypeService.purchaseTypeCheck(purchaseDTO.getPurchaseType()))
                .electroItem(electroItemService.electroItemCheck(purchaseDTO.getElectroName()))
                .employee(employeeService.employeeCheck(purchaseDTO.getEmployeeId()))
                .date(LocalDateTime.now())
                .build();

        electroShopService.setCount(purchase.getElectroItem(), purchase.getShop(), 1);

        return purchaseRepository.save(purchase).getId();
    }

    /**
     * Изменение чека покупки
     *
     * @param purchaseDTO DTO чека
     * @param id          ID чека
     */
    @Transactional
    @Override
    public void updatePurchase(PurchaseDTO purchaseDTO, Long id) {
        Purchase purchase = purchaseCheck(id);
        modelMapper.map(purchaseDTO, purchase);
        purchaseRepository.save(purchase);
    }

    /**
     * Удаление чека
     *
     * @param id ID чека
     */
    @Transactional
    @Override
    public void deletePurchase(Long id) {
        purchaseRepository.delete(purchaseCheck(id));

    }

    /**
     * Получение чека по ID
     *
     * @param id ID чека
     * @return {@link PurchaseDTO} чек
     */
    @Transactional
    @Override
    public PurchaseResponse getPurchase(Long id) {
        return modelMapper.map(purchaseCheck(id), PurchaseResponse.class);
    }

    /**
     * Получение всех чеков
     *
     * @return List {@link PurchaseResponse} чеки
     */
    @Transactional
    @Override
    public List<PurchaseResponse> getAllPurchases(boolean sort, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (sort) {
            return modelMapper.map(purchaseRepository.findAllByOrderByDateDesc(pageRequest),
                    new TypeToken<List<PurchaseResponse>>() {
                    }.getType());
        } else {
            return modelMapper.map(purchaseRepository.findAll(pageRequest).getContent(),
                    new TypeToken<List<PurchaseResponse>>() {
                    }.getType());
        }
    }

    /**
     * Проверка транзакции
     *
     * @param id ID транзакции
     * @return {@link Purchase}
     */
    private Purchase purchaseCheck(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Транзакция не найдена"));
    }
}
