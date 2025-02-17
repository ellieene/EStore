package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.PurchaseDTO;
import ru.isands.test.estore.model.request.PurchasesDropRequest;
import ru.isands.test.estore.model.response.PurchaseResponse;
import ru.isands.test.estore.service.impl.ElectroShopServiceImpl;
import ru.isands.test.estore.service.impl.PurchaseServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@CrossOrigin(origins = {"http://localhost:8082", "http://127.0.0.1:8082"})
@Tag(name = "Транзакции")
public class PurchaseController {

    private final PurchaseServiceImpl purchaseService;
    private final ElectroShopServiceImpl electroShopService;

    @PostMapping
    @Operation(summary = "Сделать транзакцию")
    public ResponseEntity<Long> createPurchase(@RequestBody PurchaseDTO purchase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.createPurchase(purchase));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение транзакции")
    public ResponseEntity<Void> updatePurchase(@PathVariable("id") Long id, @RequestBody PurchaseDTO purchase) {
        purchaseService.updatePurchase(purchase, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение транзакции")
    public ResponseEntity<PurchaseResponse> getPurchase(@PathVariable("id") Long id) {
        return ResponseEntity.ok(purchaseService.getPurchase(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех транзакций")
    public ResponseEntity<List<PurchaseResponse>> getPurchases(@RequestParam boolean sort,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(purchaseService.getAllPurchases(sort, page, size));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление транзакции")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/order")
    @Operation(summary = "Заказ товара в магазин")
    public void test(@RequestBody PurchasesDropRequest purchasesDropRequest) {
        electroShopService.setCountTest(purchasesDropRequest);
    }
}
