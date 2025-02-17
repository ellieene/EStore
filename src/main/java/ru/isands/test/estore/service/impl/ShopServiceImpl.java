package ru.isands.test.estore.service.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityExist;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.ShopDTO;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.repository.ShopRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    /**
     * Создание магазина
     *
     * @param shopDto магазин
     * @return ID магазина
     */
    @Transactional
    public Long createShop(ShopDTO shopDto) {
        if (shopRepository.existsByNameAndAddress(shopDto.getName(), shopDto.getAddress())) {
            throw new EntityExist("Такой магазин уже есть");
        }
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
    public void updateShop(Long id, ShopDTO shopDto) {
        Shop shop = shopRepository.findById(id).
                orElseThrow(() -> new EntityNotFound("Магазин не найден"));
        modelMapper.map(shopDto, shop);
        shopRepository.save(shop);
    }

    /**
     * Удаление магазина
     *
     * @param id магазина
     */
    @Transactional
    public void deleteShop(Long id) {
        shopRepository.deleteById(id);
    }

    /**
     * Получить один магазин
     *
     * @param id магазина
     * @return {@link ShopDTO}
     */
    @Transactional
    public ShopDTO getShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Магазин не найден"));
        return modelMapper.map(shop, ShopDTO.class);
    }

    /**
     * Получить все магазины
     *
     * @return List {@link ShopDTO}
     */
    @Transactional
    public List<ShopDTO> getAllShops() {
        return modelMapper.map(shopRepository.findAll(), new TypeToken<List<ShopDTO>>() {
        }.getType());
    }
}
