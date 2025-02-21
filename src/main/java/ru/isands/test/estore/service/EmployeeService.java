package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.EmployeeDTO;
import ru.isands.test.estore.model.entity.Employee;

import java.util.List;


public interface EmployeeService {

    /**
     * Добавление нового сотрудника
     *
     * @param employeeDTO новый сотрудник
     * @return ID нового сотрудника
     */
    Long addEmployee(EmployeeDTO employeeDTO);

    /**
     * Обновляем сотрудника
     *
     * @param employeeDTO сотрудник
     * @param id          сотрудника которого хотим изменить
     */
    void updateEmployee(EmployeeDTO employeeDTO, long id);

    /**
     * Удаление сотрудника
     *
     * @param id сотрудника
     */
    void deleteEmployee(long id);

    /**
     * Получение сотрудника
     *
     * @param id сотрудника
     * @return {@link EmployeeDTO}
     */
    EmployeeDTO getEmployee(long id);

    /**
     * Получение всех сотрудников
     *
     * @return List {@link EmployeeDTO}
     */
    List<EmployeeDTO> getAllEmployees(int page, int size);

    /**
     * Проверка сотрудника
     *
     * @param id ID сотрудника
     * @return {@link Employee}
     */
    Employee employeeCheck(long id);
}
