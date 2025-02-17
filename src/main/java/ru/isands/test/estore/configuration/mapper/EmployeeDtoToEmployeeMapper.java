package ru.isands.test.estore.configuration.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;
import ru.isands.test.estore.exception.JobTitleNotFoundException;
import ru.isands.test.estore.model.dto.EmployeeDTO;
import ru.isands.test.estore.model.entity.Employee;
import ru.isands.test.estore.model.entity.directory.Position;
import ru.isands.test.estore.repository.JobTitleRepository;

import javax.annotation.PostConstruct;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class EmployeeDtoToEmployeeMapper {

    private final ModelMapper modelMapper;
    private final JobTitleRepository jobTitleRepository;

    /**
     * Преобразование {@link EmployeeDTO} в {@link Employee}
     */
    @PostConstruct
    public void addEmployeeDtoToModelMapper() {
        try {
            TypeMap<EmployeeDTO, Employee> employeeMapper = modelMapper.createTypeMap(EmployeeDTO.class, Employee.class);
            employeeMapper.addMappings(m -> {
                m.using(getJobTitleConverter()).map(EmployeeDTO::getPosition, Employee::setPosition);
                m.skip(Employee::setGender);
            });
        } catch (Exception e) {
            log.error("Ошибка при настройке ModelMapper: ", e);
        }
    }

    /**
     * Конвертор из должности в сущность
     *
     * @return {@link Position}
     */
    private Converter<String, Position> getJobTitleConverter() {
        return mappingContext -> {
            String jobTitle = mappingContext.getSource();

            if (jobTitle == null || jobTitle.isBlank()) {
                throw new JobTitleNotFoundException("Должность не указана");
            }
            return jobTitleRepository.findByName(jobTitle)
                    .orElseThrow(() -> new JobTitleNotFoundException("Должность '" + jobTitle + "' не найдена"));
        };
    }
}