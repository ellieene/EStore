package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ShopDTO {

    @Schema(description = "Название магазина", example = "Пятерочка", required = true)
    @Size(max = 150,  message = "Название не должно превышать 150 символов")
    private String name;

    @Schema(description = "Адрес магазина", example = "Карла Маркса 6", required = true)
    private String address;
}
