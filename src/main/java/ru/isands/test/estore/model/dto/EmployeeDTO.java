package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO для Сотрудника
 */
@Data
public class EmployeeDTO {

    @Schema(description = "Имя", example = " Валерий", required = true)
    private String lastName;

    @Schema(description = "Фамилия", example = "Смирнов", required = true)
    private String firstName;

    @Schema(description = "Отчество", example = "Сергеевич", required = false)
    private String patronymic;

    @Schema(description = "Дата рождения", example = "2003-02-06", required = true)
    private LocalDate birthDate;

    @Schema(description = "Должность", example = "Главный-кассир", required = true)
    private String position;

    @Schema(description = "Магазин", required = true)
    private ShopDTO shop;

    @Schema(description = "Пол", example = "Мужчина", required = true)
    private String gender;
}
