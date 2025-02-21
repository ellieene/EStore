package ru.isands.test.estore.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;
import ru.isands.test.estore.model.dto.EmployeeDTO;
import ru.isands.test.estore.model.entity.Employee;
import ru.isands.test.estore.model.entity.directory.Position;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class EmployeeToEmployeeDtoMapper {

    private final ModelMapper modelMapper;

    /**
     * Преобразование {@link Employee} в {@link EmployeeDTO}
     */
    @PostConstruct
    public void addEmployeeToModel() {
        TypeMap<Employee, EmployeeDTO> employeeMapper = modelMapper.createTypeMap(Employee.class, EmployeeDTO.class);
        employeeMapper.addMappings(m -> {
            m.using(getJobTitleConverter()).map(Employee::getPosition, EmployeeDTO::setPosition);
            m.using(getGenderConverter()).map(Employee::getGender, EmployeeDTO::setGender);
        });
    }

    /**
     * Конвертор из сущности в должность
     *
     * @return String
     */
    private Converter<Position, String> getJobTitleConverter() {
        return mappingContext -> {
            Position position = mappingContext.getSource();
            return position != null ? position.getName() : null;
        };
    }

    /**
     * Конвертор из boolean в string
     *
     * @return String
     */
    private Converter<Boolean, String> getGenderConverter() {
        return mappingContext -> {
            Boolean gender = mappingContext.getSource();
            if (Boolean.TRUE.equals(gender)) {
                return "Мужчина";
            } else {
                return "Женщина";
            }
        };
    }


}
