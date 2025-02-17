package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.ElectroItemDTO;
import ru.isands.test.estore.service.impl.ElectroItemServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8082", "http://127.0.0.1:8082"})
@RequestMapping("/electro-item")
@Tag(name = "Товары")
public class ElectroItemController {

    private final ElectroItemServiceImpl electroItemServiceImpl;

    @PostMapping
    @Operation(summary = "Создание нового товара")
    public ResponseEntity<Long> addElectroItem(@RequestBody ElectroItemDTO electroItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(electroItemServiceImpl.addElectroItem(electroItemDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение товара")
    public ResponseEntity<Void> updateElectroItem(@RequestBody ElectroItemDTO electroItemDTO, @PathVariable Long id) {
        electroItemServiceImpl.updateElectroItem(electroItemDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление товара")
    public void deleteElectroItem(@PathVariable Long id) {
        electroItemServiceImpl.deleteElectroItem(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение товара")
    public ResponseEntity<ElectroItemDTO> ElectroItems(@PathVariable Long id) {
        return ResponseEntity.ok(electroItemServiceImpl.getElectroItem(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех товаров")
    public ResponseEntity<List<ElectroItemDTO>> ElectroItemsAll(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(electroItemServiceImpl.getElectroItemAll(page, size));
    }

}
