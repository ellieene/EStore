package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PurchaseTypeDTO {

    @Schema(description = "Название тип оплаты", example = "Наличные", required = true)
    private String name;
}
