package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ElectroTypeDTO {

    @Schema(description = "Название типа", example = "Телефон", required = true)
    @Size(max = 150,  message = "Название не должно превышать 150 символов")
    private String name;
}
