package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO для Сотрудника
 */
@Data
public class EmployeeDTO {

    @Schema(description = "ID", example = "1", required = true)
    private Long id;

    @Schema(description = "Имя", example = " Валерий", required = true)
    @Size(max = 100,  message = "Имя не должно превышать 100 символов")
    private String lastName;

    @Schema(description = "Фамилия", example = "Смирнов", required = true)
    @Size(max = 100,  message = "Фамилия не должна превышать 100 символов")
    private String firstName;

    @Schema(description = "Отчество", example = "Сергеевич", required = false)
    @Size(max = 100,  message = "Отчество не должно превышать 100 символов")
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
