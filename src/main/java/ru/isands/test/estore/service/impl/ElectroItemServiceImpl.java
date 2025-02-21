package ru.isands.test.estore.service.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityExist;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.ElectroItemDTO;
import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.repository.ElectroItemRepository;
import ru.isands.test.estore.service.ElectroItemService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Tag(name = "Электротовары")
public class ElectroItemServiceImpl implements ElectroItemService {

    private final ElectroItemRepository repository;
    private final ModelMapper modelMapper;

    /**
     * Создание нового товара
     *
     * @param electroItemDTO DTO товара
     * @return ID нового товара
     */
    @Transactional
    @Override
    public Long addElectroItem(ElectroItemDTO electroItemDTO) {
        repository.findByName(electroItemDTO.getName())
                .ifPresent((e) -> {
                    throw new EntityExist("Такой товар уже есть");
                });

        ElectroItem electroItem = modelMapper.map(electroItemDTO, ElectroItem.class);
        return repository.save(electroItem).getId();
    }

    /**
     * Изменение товара
     *
     * @param electroItemDTO {@link ElectroItemDTO}
     * @param id             ID товара
     */
    @Transactional
    @Override
    public void updateElectroItem(ElectroItemDTO electroItemDTO, long id) {
        repository.findByName(electroItemDTO.getName())
                .ifPresent(item -> {
                    throw new EntityExist("Такой товар уже есть");
                });

        ElectroItem electroItem = electroItemCheck(id);
        modelMapper.map(electroItemDTO, electroItem);
        repository.save(electroItem);
    }

    /**
     * Получение товара
     *
     * @param id ID товара
     * @return {@link ElectroItemDTO}
     */
    @Transactional
    @Override
    public ElectroItemDTO getElectroItem(long id) {
        return modelMapper.map(electroItemCheck(id), ElectroItemDTO.class);
    }

    /**
     * Получение всех товаров
     *
     * @return List {@link ElectroItemDTO}
     */
    @Transactional
    @Override
    public List<ElectroItemDTO> getElectroItemAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return modelMapper.map(repository
                .findAll(pageRequest).getContent(), new TypeToken<List<ElectroItemDTO>>() {
        }.getType());
    }

    /**
     * Удаление товара
     *
     * @param id ID товара
     */
    @Transactional
    @Override
    public void deleteElectroItem(long id) {
        repository.delete(electroItemCheck(id));
    }

    /**
     * Проверка товара через поле name
     *
     * @param name Название товара
     * @return {@link ElectroItem}
     */
    @Transactional
    @Override
    public ElectroItem electroItemCheck(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFound("Товар не найден"));
    }

    /**
     * Проверка товара
     *
     * @param id ID товара
     * @return {@link ElectroItem}
     */
    private ElectroItem electroItemCheck(long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFound("Товар не найден"));
    }

}
