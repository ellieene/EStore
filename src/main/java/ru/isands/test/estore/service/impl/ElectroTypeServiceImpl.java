package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityExist;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.ElectroTypeDTO;
import ru.isands.test.estore.model.entity.directory.ElectroType;
import ru.isands.test.estore.repository.ElectroTypeRepository;
import ru.isands.test.estore.service.ElectroTypeService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectroTypeServiceImpl implements ElectroTypeService {

    private final ElectroTypeRepository electroTypeRepository;
    private final ModelMapper modelMapper;


    /**
     * Создание типа товара
     *
     * @param typeDTO {@link ElectroTypeDTO}
     * @return ID типа товара
     */
    @Transactional
    @Override
    public Long createType(ElectroTypeDTO typeDTO) {
        electroTypeRepository.findByName(typeDTO.getName())
                .ifPresent((e) -> {
                    throw new EntityExist("Такой тип товара уже есть");
                });

        ElectroType electroType = modelMapper.map(typeDTO, ElectroType.class);
        return electroTypeRepository.save(electroType).getId();
    }

    /**
     * Изменение типа товара
     *
     * @param electroTypeDTO {@link ElectroTypeDTO}
     * @param id             ID типа товара
     */
    @Transactional
    @Override
    public void updateType(ElectroTypeDTO electroTypeDTO, long id) {
        electroTypeRepository.findByName(electroTypeDTO.getName())
                .ifPresent((e) -> {
                    throw new EntityExist("Такой тип товара уже есть");
                });

        ElectroType electroType = electroTypeCheck(id);
        modelMapper.map(electroTypeDTO, electroType);
        electroTypeRepository.save(electroType);
    }

    /**
     * Удаление типа товаара
     *
     * @param id ID типа товара
     */
    @Transactional
    @Override
    public void deleteType(long id) {
        electroTypeRepository.delete(electroTypeCheck(id));
    }

    /**
     * Получить тип товара
     *
     * @param id ID типа товара
     * @return {@link ElectroTypeDTO}
     */
    @Transactional
    @Override
    public ElectroTypeDTO getType(long id) {
        return modelMapper.map(electroTypeCheck(id), ElectroTypeDTO.class);
    }

    /**
     * Получение всех типов товара
     *
     * @return List {@link ElectroType}
     */
    @Transactional
    @Override
    public List<ElectroType> getAllTypes(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return electroTypeRepository.findAll(pageRequest).getContent();
    }

    /**
     * Проверка на тип товара
     *
     * @param id ID тип товара
     * @return {@link ElectroType}
     */
    private ElectroType electroTypeCheck(long id) {
        return electroTypeRepository.findById(id).orElseThrow(() -> new EntityNotFound("Тип товара не найден"));
    }
}
