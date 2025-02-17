package ru.isands.test.estore.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.EmployeeDTO;
import ru.isands.test.estore.service.impl.EmployeeServiceImpl;

import java.util.List;


/**
 * Контроллер сотрудника
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@CrossOrigin(origins = {"http://localhost:8082", "http://127.0.0.1:8082"})
@Tag(name = " Сотрудники")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @PostMapping
    @Operation(summary = "Добавления сотрудника")
    public ResponseEntity<Long> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeServiceImpl.addEmployee(employeeDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение сотрудника")
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id) {
        employeeServiceImpl.updateEmployee(employeeDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление сотрудника")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeServiceImpl.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение сотрудника")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeServiceImpl.getEmployee(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех сотрудников")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(employeeServiceImpl.getAllEmployees(page, size));
    }
}
