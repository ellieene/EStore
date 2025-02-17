package ru.isands.test.estore.model.entity.directory;

import lombok.Data;

import javax.persistence.*;

/**
 * Тип покупки
 */
@Data
@Entity
public class PurchaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;
}
