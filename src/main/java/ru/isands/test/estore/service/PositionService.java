package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.PositionDTO;
import ru.isands.test.estore.model.entity.directory.Position;

import java.util.List;

public interface PositionService {

    /**
     * Создание должности
     *
     * @param positionDto должность
     * @return ID должности
     */
    Long createJobTitle(PositionDTO positionDto);

    /**
     * Изменение должности
     *
     * @param positionDTO DTO должности
     * @param id          ID должности
     */
    void updateJobTitle(PositionDTO positionDTO, long id);

    /**
     * Удаление должности
     *
     * @param id ID должности
     */
    void deleteJobTitle(long id);

    /**
     * Получение должности
     *
     * @param id ID должности
     * @return {@link PositionDTO}
     */
    PositionDTO getJobTitle(long id);

    /**
     * Получение всех должностей
     *
     * @return List {@link Position}
     */
    List<Position> getJobTitles(int page, int size);
}
