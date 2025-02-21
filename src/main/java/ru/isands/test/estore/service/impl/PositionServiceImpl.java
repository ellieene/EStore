package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityExist;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.PositionDTO;
import ru.isands.test.estore.model.entity.directory.Position;
import ru.isands.test.estore.repository.JobTitleRepository;
import ru.isands.test.estore.service.PositionService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final JobTitleRepository jobTitleRepository;
    private final ModelMapper modelMapper;


    /**
     * Создание должности
     *
     * @param positionDto должность
     * @return ID должности
     */
    @Transactional
    @Override
    public Long createJobTitle(PositionDTO positionDto) {
        jobTitleRepository.findByName(positionDto.getName())
                .ifPresent((e) -> {
                    throw new EntityExist("Такая должность уже есть");
                });

        Position position1 = modelMapper.map(positionDto, Position.class);
        return jobTitleRepository.save(position1).getId();
    }

    /**
     * Изменение должности
     *
     * @param positionDTO DTO должности
     * @param id          ID должности
     */
    @Transactional
    @Override
    public void updateJobTitle(PositionDTO positionDTO, long id) {
        jobTitleRepository.findByName(positionDTO.getName())
                .ifPresent(job -> {
                    throw new EntityExist("Такая должность уже есть");
                });

        Position position = positionCheck(id);
        modelMapper.map(positionDTO, position);
        jobTitleRepository.save(position);
    }

    /**
     * Удаление должности
     *
     * @param id ID должности
     */
    @Transactional
    @Override
    public void deleteJobTitle(long id) {
        jobTitleRepository.delete(positionCheck(id));
    }

    /**
     * Получение должности
     *
     * @param id ID должности
     * @return {@link PositionDTO}
     */
    @Transactional
    @Override
    public PositionDTO getJobTitle(long id) {
        Position position = positionCheck(id);
        return modelMapper.map(position, PositionDTO.class);
    }

    /**
     * Получение всех должностей
     *
     * @return List {@link Position}
     */
    @Transactional
    @Override
    public List<Position> getJobTitles(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return jobTitleRepository.findAll(pageRequest).getContent();
    }

    /**
     * Проверка на должность
     *
     * @param id ID должности
     * @return {@link Position}
     */
    private Position positionCheck(long id) {
        return jobTitleRepository.findById(id).orElseThrow(() -> new EntityNotFound("Должность не найдена"));
    }
}
