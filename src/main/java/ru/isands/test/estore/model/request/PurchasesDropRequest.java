package ru.isands.test.estore.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.isands.test.estore.model.dto.ShopDTO;

@Data

public class PurchasesDropRequest {

    @Schema(description = "Название товара", example = "Шуроповерт Titan-300w", required = true)
    private String name;

    private ShopDTO shop;

    @Schema(description = "Количество товара", example = "5", required = true)
    private int count;
}
