package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для товара
 */
@Data
public class ElectroItemDTO {

    @Schema(description = "ID", example = "1", required = true)
    private Long id;

    @Schema(description = "Название товара", example = "Шуроповерт Titan-300w", required = true)
    private String name;

    @Schema(description = "Цена", example = "3000", required = true)
    private Long price;

    @Schema(description = "Тип электроники", example = "Бытовой инструмент", required = true)
    private String electronicType;

    @Schema(description = "признак архивности", example = "false", required = true)
    private boolean archive;

    @Schema(description = "Описание", example = "Шуроповер Titan-300w, широкодоступный для домашнего использования", required = false)
    private String description;
}
