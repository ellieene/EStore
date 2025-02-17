package ru.isands.test.estore.model.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeResponse {

    private String lastName;

    private String firstName;

    private String patronymic;

    private LocalDate birthDate;

    private String position;

}
