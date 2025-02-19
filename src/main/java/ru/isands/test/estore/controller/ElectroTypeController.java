package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.ElectroTypeDTO;
import ru.isands.test.estore.model.entity.directory.ElectroType;
import ru.isands.test.estore.service.impl.ElectroTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/electro-type")
@CrossOrigin(origins = {"http://localhost:8082", "http://127.0.0.1:8082"})
@Tag(name = "Типы товаров")
public class ElectroTypeController {

    private final ElectroTypeServiceImpl electroTypeServiceImpl;

    @PostMapping
    @Operation(summary = "Создание типа товаров")
    public ResponseEntity<Long> createType(@RequestBody ElectroTypeDTO electroTypeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(electroTypeServiceImpl.createType(electroTypeDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип товара")
    public ResponseEntity<ElectroTypeDTO> getType(@PathVariable Long id) {
        return ResponseEntity.ok(electroTypeServiceImpl.getType(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех типов товара")
    public ResponseEntity<List<ElectroType>> getAllType(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(electroTypeServiceImpl.getAllTypes(page, size));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение типа товара")
    public ResponseEntity<Void> updateType(@PathVariable Long id, @RequestBody ElectroTypeDTO electroTypeDTO) {
        electroTypeServiceImpl.updateType(electroTypeDTO, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление типа товара")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        electroTypeServiceImpl.deleteType(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
