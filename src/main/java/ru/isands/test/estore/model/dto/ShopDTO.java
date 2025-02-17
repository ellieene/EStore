package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopDTO {

    @Schema(description = "Название магазина", example = "Пятерочка", required = true)
    private String name;

    @Schema(description = "Адрес магазина", example = "Карла Маркса 6", required = true)
    private String address;
}
