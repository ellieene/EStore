package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.PositionDTO;
import ru.isands.test.estore.model.entity.directory.Position;

import java.util.List;

public interface PositionService {

    Long createJobTitle(PositionDTO jobTitle);

    void updateJobTitle(PositionDTO positionDTO, long id);

    void deleteJobTitle(long id);

    PositionDTO getJobTitle(long id);

    List<Position> getJobTitles(int page, int size);
}
