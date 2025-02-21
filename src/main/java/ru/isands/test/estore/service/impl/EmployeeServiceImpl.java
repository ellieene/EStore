package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.exception.GenderException;
import ru.isands.test.estore.model.dto.EmployeeDTO;
import ru.isands.test.estore.model.entity.Employee;
import ru.isands.test.estore.model.enums.Gender;
import ru.isands.test.estore.repository.EmployeeRepository;
import ru.isands.test.estore.service.EmployeeService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Сервис сотрудника
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final ShopServiceImpl shopService;
    private final EmployeeRepository employeeRepository;

    /**
     * Добавление нового сотрудника
     *
     * @param employeeDTO новый сотрудник
     * @return ID нового сотрудника
     */
    @Transactional
    @Override
    public Long addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        employee.setShop(shopService.shopCheckInDTO(employeeDTO.getShop()));
        employee.setGender(genderCheck(employeeDTO.getGender()));
        employeeRepository.save(employee);
        return employee.getId();
    }

    /**
     * Обновляем сотрудника
     *
     * @param employeeDTO сотрудник
     * @param id          сотрудника которого хотим изменить
     */
    @Transactional
    @Override
    public void updateEmployee(EmployeeDTO employeeDTO, long id) {
        Employee employee = employeeCheck(id);

        modelMapper.map(employeeDTO, employee);
        employee.setShop(shopService.shopCheckInDTO(employeeDTO.getShop()));
        employeeRepository.save(employee);
    }

    /**
     * Удаление сотрудника
     *
     * @param id сотрудника
     */
    @Transactional
    @Override
    public void deleteEmployee(long id) {
        employeeRepository.delete(employeeCheck(id));
    }

    /**
     * Получение сотрудника
     *
     * @param id сотрудника
     * @return {@link EmployeeDTO}
     */
    @Transactional
    @Override
    public EmployeeDTO getEmployee(long id) {
        Employee employee = employeeCheck(id);
        return modelMapper.map(employee, EmployeeDTO.class);
    }


    /**
     * Получение всех сотрудников
     *
     * @return List {@link EmployeeDTO}
     */
    @Transactional
    @Override
    public List<EmployeeDTO> getAllEmployees(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return modelMapper.map(employeeRepository.findAll(pageRequest).getContent(),
                new TypeToken<List<EmployeeDTO>>() {
                }.getType());
    }

    /**
     * Проверка сотрудника
     *
     * @param id ID сотрудника
     * @return {@link Employee}
     */
    @Transactional
    @Override
    public Employee employeeCheck(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Сотрудник не найден"));
    }


    /**
     * Проверка гендера
     *
     * @param gender String(Мужской/Женский)
     * @return boolean
     */
    private boolean genderCheck(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new GenderException("Гендер не может быть пустым");
        }
        gender = gender.trim().substring(0, 3).toLowerCase();

        if (gender.equals(Gender.MALE.getName())) {
            return Boolean.TRUE;
        } else if (gender.equals(Gender.FEMALE.getName())) {
            return Boolean.FALSE;
        } else {
            throw new GenderException("Извините, такого гендера не существует");
        }
    }
}
