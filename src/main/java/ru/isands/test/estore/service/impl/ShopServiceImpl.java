package ru.isands.test.estore.service.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityExist;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.ShopDTO;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.repository.ShopRepository;
import ru.isands.test.estore.service.ShopService;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    /**
     * Создание магазина
     *
     * @param shopDto магазин
     * @return ID магазина
     */
    @Transactional
    @Override
    public Long createShop(ShopDTO shopDto) {
        shopRepository.findAllByNameAndAddress(shopDto.getName(), shopDto.getAddress()).ifPresent(shop -> {
            throw new EntityExist("Такой магазин уже есть");
        });

        Shop shop = modelMapper.map(shopDto, Shop.class);
        return shopRepository.save(shop).getId();
    }

    /**
     * Изменение магазина
     *
     * @param id      магазина
     * @param shopDto магазин
     */
    @Transactional
    @Override
    public void updateShop(long id, ShopDTO shopDto) {
        shopRepository.findAllByNameAndAddress(shopDto.getName(), shopDto.getAddress())
                .ifPresent(shop1 -> {
                    throw new EntityExist("Такой магазин уже есть");
                });

        Shop shop = shopCheckinId(id);
        modelMapper.map(shopDto, shop);
        shopRepository.save(shop);
    }

    /**
     * Удаление магазина
     *
     * @param id магазина
     */
    @Transactional
    @Override
    public void deleteShop(long id) {
        shopRepository.delete(shopCheckinId(id));
    }

    /**
     * Получить один магазин
     *
     * @param id магазина
     * @return {@link ShopDTO}
     */
    @Transactional
    @Override
    public ShopDTO getShop(long id) {
        Shop shop = shopCheckinId(id);
        return modelMapper.map(shop, ShopDTO.class);
    }

    /**
     * Получить все магазины
     *
     * @return List {@link ShopDTO}
     */
    @Transactional
    @Override
    public List<Shop> getAllShops(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return shopRepository.findAll(pageRequest).getContent();
    }

    /**
     * Проверка магазина через сущность
     *
     * @param shop магазин
     * @return {@link Shop}
     */
    @Transactional
    @Override
    public Shop shopCheckInDTO(ShopDTO shop) {
        return shopRepository.findAllByNameAndAddress(shop.getName(), shop.getAddress())
                .orElseThrow(() -> new EntityNotFound("Магазин не найден"));
    }

    /**
     * Проверка магазина через ID
     *
     * @param id ID магазина
     * @return {@link Shop}
     */
    private Shop shopCheckinId(long id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Магазин не найден"));
    }
}
