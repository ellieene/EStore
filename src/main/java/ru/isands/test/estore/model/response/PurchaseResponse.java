package ru.isands.test.estore.model.response;

import lombok.Data;
import ru.isands.test.estore.model.dto.ShopDTO;

import java.time.LocalDateTime;

@Data
public class PurchaseResponse {

    private Long id;

    private String electroName;

    private EmployeeResponse employee;

    private LocalDateTime date;

    private String purchaseType;

    private ShopDTO shop;
}
