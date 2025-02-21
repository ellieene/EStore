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
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
    @TableGenerator(name = "id_generator", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_value", allocationSize = 1)
    private Long id;

    private String name;
}
