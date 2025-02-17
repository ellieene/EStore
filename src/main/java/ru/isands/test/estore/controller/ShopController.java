package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.ShopDTO;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.model.response.ElectroItemResponce;
import ru.isands.test.estore.service.impl.ElectroShopServiceImpl;
import ru.isands.test.estore.service.impl.ShopServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8082", "http://127.0.0.1:8082"})
@RequestMapping("/shop")
@Tag(name = "Магазины")
public class ShopController {

    private final ShopServiceImpl shopService;
    private final ElectroShopServiceImpl electroShopServiceImpl;

    @PostMapping
    @Operation(summary = "Создание магазина")
    public ResponseEntity<Long> createShop(ShopDTO shop) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shopService.createShop(shop));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение магазина")
    public ResponseEntity<Void> updateShop(@PathVariable("id") Long id, @RequestBody ShopDTO shop) {
        shopService.updateShop(id, shop);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение магазина")
    public ResponseEntity<ShopDTO> getShop(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shopService.getShop(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех магазинов")
    public ResponseEntity<List<Shop>> getShops(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(shopService.getAllShops(page, size));
    }

    @GetMapping("/item")
    @Operation(summary = "Товары этого магазина")
    public ResponseEntity<List<ElectroItemResponce>> getShopItem(@RequestParam Long id,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(electroShopServiceImpl.getItemInShop(id, page, size));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление магазина")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
