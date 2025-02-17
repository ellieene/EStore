package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ElectroTypeDTO;
import ru.isands.test.estore.model.entity.directory.ElectroType;

import java.util.List;

public interface ElectroTypeService {

    Long createType(ElectroTypeDTO electroTypeDTO);

    void updateType(ElectroTypeDTO electroTypeDTO, Long id);

    void deleteType(Long id);

    ElectroTypeDTO getType(Long id);

    List<ElectroType> getAllTypes(int page, int size);
}
