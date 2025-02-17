package ru.isands.test.estore.service.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.ElectroItemDTO;
import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.repository.ElectroItemRepository;
import ru.isands.test.estore.repository.ElectronicsTypeRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Tag(name = "Электротовары")
public class ElectroItemService {

    private final ElectroItemRepository repository;
    private final ElectronicsTypeRepository typeRepository;
    private final ModelMapper modelMapper;

    /**
     * Создание нового товара
     * @param electroItemDTO DTO товара
     * @return ID нового товара
     */
    @Transactional
    public Long addElectroItem(ElectroItemDTO electroItemDTO) {
        ElectroItem electroItem = modelMapper.map(electroItemDTO, ElectroItem.class);
        return repository.save(electroItem).getId();
    }

    /**
     * Изменение товара
     * @param item {@link ElectroItemDTO}
     * @param id ID товара
     */
    @Transactional
    public void updateElectroItem(ElectroItemDTO item, Long id) {
        ElectroItem electroItem = repository.findById(id)
                .orElseThrow(()-> new EntityNotFound("Товар не найдер"));

        modelMapper.map(item, electroItem);
        repository.save(electroItem);
    }

    /**
     * Получение товара
     * @param id ID товара
     * @return {@link ElectroItemDTO}
     */
    @Transactional
    public ElectroItemDTO getElectroItem(Long id) {
        ElectroItem electroItem = repository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Товар не найден"));

        return modelMapper.map(electroItem, ElectroItemDTO.class);
    }

    /**
     * Получение всех товаров
     * @return List {@link ElectroItemDTO}
     */
    @Transactional
    public List<ElectroItemDTO> getElectroItemAll() {
        return modelMapper.map(repository
                .findAll(), new TypeToken<List<ElectroItemDTO>>() {}.getType());
    }

    /**
     * Удаление товара
     * @param id ID товара
     */
    @Transactional
    public void deleteElectroItem(Long id) {
        ElectroItem electroItem = repository.findById(id).orElseThrow(()-> new EntityNotFound("Товар не найден"));
        repository.delete(electroItem);
    }
}
