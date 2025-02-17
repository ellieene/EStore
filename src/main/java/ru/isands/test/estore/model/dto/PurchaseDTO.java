package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseDTO {

    @Schema(description = "Название товара", example = "Шуроповерт Titan-300w", required = true)
    private String electroName;

    @Schema(description = "Индивидуальный номер сотрудника (ID)", example = "77", required = true)
    private Long employeeId;

    @Schema(description = "Тип оплаты", example = "Наличные", required = true)
    private String purchaseType;

    @Schema(description = "Магазин", required = true)
    private ShopDTO shop;

}

