package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityExist;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.PositionDTO;
import ru.isands.test.estore.model.entity.directory.Position;
import ru.isands.test.estore.repository.JobTitleRepository;
import ru.isands.test.estore.service.JobTitleService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {

    private final JobTitleRepository jobTitleRepository;
    private final ModelMapper modelMapper;


    /**
     * Создание должности
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
     * @param positionDTO DTO должности
     * @param id ID должности
     */
    @Transactional
    @Override
    public void updateJobTitle(PositionDTO positionDTO, Long id) {
        Position position = jobTitleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Должность не найдена"));

        modelMapper.map(positionDTO, position);
        jobTitleRepository.save(position);
    }

    /**
     * Удаление должности
     * @param id ID должности
     */
    @Transactional
    @Override
    public void deleteJobTitle(Long id) {
        jobTitleRepository.findById(id).orElseThrow(() -> new EntityNotFound("Должность не найдена"));
        jobTitleRepository.deleteById(id);
    }

    /**
     * Получение должности
     * @param id ID должности
     * @return {@link PositionDTO}
     */
    @Transactional
    @Override
    public PositionDTO getJobTitle(Long id) {
        Position position = jobTitleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Должность не найдена"));
        return modelMapper.map(position, PositionDTO.class);
    }

    /**
     * Получение всех должностей
     * @return List {@link Position}
     */
    @Transactional
    @Override
    public List<Position> getJobTitles() {
        return jobTitleRepository.findAll();
    }
}
