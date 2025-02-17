package ru.isands.test.estore.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.exception.GenderException;
import ru.isands.test.estore.model.dto.EmployeeDTO;
import ru.isands.test.estore.model.dto.ShopDTO;
import ru.isands.test.estore.model.entity.Employee;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.model.enums.Gender;
import ru.isands.test.estore.repository.EmployeeRepository;
import ru.isands.test.estore.repository.ShopRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Сервис сотрудника
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ModelMapper modelMapper;
    private final ShopRepository shopRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Добавление нового сотрудника
     *
     * @param employeeDTO новый сотрудник
     * @return ID нового сотрудника
     */
    @Transactional
    public Long addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        employee.setShop(storeCheck(employeeDTO.getShop()));
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
    public void updateEmployee(EmployeeDTO employeeDTO, Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Сотрудник не найден"));

        modelMapper.map(employeeDTO, employee);
        employee.setShop(storeCheck(employeeDTO.getShop()));
        employeeRepository.save(employee);
    }

    /**
     * Удаление сотрудника
     *
     * @param id сотрудника
     */
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Сотрудник не найден"));
        employeeRepository.delete(employee);
    }

    /**
     * Получение сотрудника
     *
     * @param id сотрудника
     * @return {@link EmployeeDTO}
     */
    @Transactional
    public EmployeeDTO getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Сотрудник не найден"));
        return modelMapper.map(employee, EmployeeDTO.class);
    }


    /**
     * Получение всех сотрудников
     *
     * @return List {@link EmployeeDTO}
     */
    @Transactional
    public List<EmployeeDTO> getAllEmployees() {
        return modelMapper.map(employeeRepository.findAll(),
                new TypeToken<List<EmployeeDTO>>() {
                }.getType());
    }


    /**
     * Проверка магазина
     *
     * @param shop магазин
     * @return {@link Shop}
     */
    private Shop storeCheck(ShopDTO shop) {
        return shopRepository.findAllByNameAndAddress(shop.getName(), shop.getAddress())
                .orElseThrow(() -> new EntityNotFound("Магазин не найден"));
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
