package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.PurchaseTypeDTO;
import ru.isands.test.estore.model.entity.directory.PurchaseType;
import ru.isands.test.estore.service.impl.PurchaseTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pyrchase-type")
@Tag(name = "Тип оплаты")
public class PurchaseTypeController {

    private final PurchaseTypeServiceImpl purchaseTypeService;

    @PostMapping
    @Operation(summary = "Создание типа оплаты")
    public ResponseEntity<Long> createType(@RequestBody PurchaseTypeDTO PurchaseTypeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseTypeService.createPurchaseType(PurchaseTypeDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип оплаты")
    public ResponseEntity<PurchaseTypeDTO> getType(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseTypeService.getPurchaseType(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех типов оплаты")
    public ResponseEntity<List<PurchaseType>> getAllType(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(purchaseTypeService.getAllPurchaseTypes(page, size));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение типа оплаты")
    public ResponseEntity<Void> updateType(@PathVariable Long id, @RequestBody PurchaseTypeDTO PurchaseTypeDTO) {
        purchaseTypeService.updatePurchaseType(PurchaseTypeDTO, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление типа оплаты")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        purchaseTypeService.deletePurchaseType(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
