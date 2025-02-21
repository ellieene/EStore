package ru.isands.test.estore.service;

import ru.isands.test.estore.model.dto.EmployeeDTO;
import ru.isands.test.estore.model.entity.Employee;

import java.util.List;


public interface EmployeeService {

    Long addEmployee(EmployeeDTO employeeDTO);

    void updateEmployee(EmployeeDTO employeeDTO, long id);

    void deleteEmployee(long id);

    EmployeeDTO getEmployee(long id);

    List<EmployeeDTO> getAllEmployees(int page, int size);

    Employee employeeCheck(long id);
}
