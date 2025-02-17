package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ElectroTypeDTO {

    @Schema(description = "Название типа", example = "Телефон", required = true)
    private String name;
}
