package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JobTitleDTO {

    @Schema(description = "Название должности", example = "Кассир", required = true)
    private String name;
}
