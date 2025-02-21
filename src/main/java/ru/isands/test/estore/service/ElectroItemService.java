package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.ElectroItemDTO;
import ru.isands.test.estore.model.entity.ElectroItem;

import java.util.List;

public interface ElectroItemService {

    Long addElectroItem(ElectroItemDTO electroItemDTO);

    void updateElectroItem(ElectroItemDTO item, long id);

    ElectroItemDTO getElectroItem(long id);

    List<ElectroItemDTO> getElectroItemAll(int page, int size);

    void deleteElectroItem(long id);

    ElectroItem electroItemCheck(String name);
}
