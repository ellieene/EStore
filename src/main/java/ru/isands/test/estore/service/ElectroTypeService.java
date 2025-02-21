package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ElectroTypeDTO;
import ru.isands.test.estore.model.entity.directory.ElectroType;

import java.util.List;

public interface ElectroTypeService {

    Long createType(ElectroTypeDTO electroTypeDTO);

    void updateType(ElectroTypeDTO electroTypeDTO, long id);

    void deleteType(long id);

    ElectroTypeDTO getType(long id);

    List<ElectroType> getAllTypes(int page, int size);
}
