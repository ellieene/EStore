package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.model.entity.ElectroShop;
import ru.isands.test.estore.model.entity.ElectroShopId;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.model.request.PurchasesDropRequest;
import ru.isands.test.estore.model.response.ElectroItemResponce;
import ru.isands.test.estore.repository.ElectroItemRepository;
import ru.isands.test.estore.repository.ElectroShopRepository;
import ru.isands.test.estore.repository.ShopRepository;
import ru.isands.test.estore.service.ElectroShopService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ElectroShopServiceImpl implements ElectroShopService {

    private final ElectroShopRepository electroShopRepository;
    private final ShopRepository shopRepository;
    private final ElectroItemRepository electroItemRepository;
    private final ModelMapper modelMapper;
    private final ShopServiceImpl shopServiceImpl;

    /**
     * Покупка товара
     *
     * @param electroItemId ID товара
     * @param shop          Магазин
     * @param count         Кол-во
     */
    @Override
    public void setCount(ElectroItem electroItemId, Shop shop, int count) {
        ElectroShopId id = new ElectroShopId(shop.getId(), electroItemId.getId());
        Optional<ElectroShop> electroShopOptional = electroShopRepository.findById(id);

        if (electroShopOptional.isPresent() && electroShopOptional.get().getCount() > 0) {
            ElectroShop electroShop = electroShopOptional.get();
            electroShop.setCount(electroShop.getCount() - count);
            electroShop.updateElectroItemCount();
            electroShopRepository.save(electroShop);
        } else {
            throw new EntityNotFound("К сожалению эта позиция закончилась в этом магазине");
        }
    }

    /**
     * Заказ товара в магазин
     *
     * @param purchasesDropRequest DTO заказа
     */
    @Override
    public void setCountTest(PurchasesDropRequest purchasesDropRequest) {
        Shop shop = shopRepository.findAllByNameAndAddress(purchasesDropRequest.getShop().getName(),
                        purchasesDropRequest
                                .getShop()
                                .getAddress())
                .orElseThrow(() -> new EntityNotFound("Магазин не найден"));

        ElectroItem electroItem = electroItemRepository.findByName(purchasesDropRequest.getName())
                .orElseThrow(() -> new EntityNotFound("Товар не найден"));

        ElectroShopId id = new ElectroShopId(shop.getId(), electroItem.getId());

        Optional<ElectroShop> electroShopOptional = electroShopRepository.findById(id);

        if (electroShopOptional.isPresent()) {
            ElectroShop electroShop = electroShopOptional.get();
            electroShop.setCount(electroShop.getCount() + purchasesDropRequest.getCount());
            electroShop.updateElectroItemCount();
            electroShopRepository.save(electroShop);
        } else {
            ElectroShop newElectroShop = new ElectroShop();
            newElectroShop.setShop(shopRepository.findById(shop.getId())
                    .orElseThrow(() -> new EntityNotFound("Магазин не найден")));
            newElectroShop.setElectroItem(electroItemRepository.findById(electroItem.getId())
                    .orElseThrow(() -> new EntityNotFound("Товар не найден")));
            newElectroShop.setCount(purchasesDropRequest.getCount());
            newElectroShop.updateElectroItemCount();
            electroShopRepository.save(newElectroShop);
        }
    }

    /**
     * Кокой и какое кол-во товара в магазине
     *
     * @param id ID магазина
     * @return List {@link ElectroItemResponce}
     */
    @Override
    public List<ElectroItemResponce> getItemInShop(Long id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ElectroShop> electroShops = electroShopRepository.findByShopId(id, pageRequest);

        return electroShops.stream()
                .map(electroShop -> modelMapper.map(electroShop.getElectroItem(), ElectroItemResponce.class))
                .collect(Collectors.toList());
    }
}
