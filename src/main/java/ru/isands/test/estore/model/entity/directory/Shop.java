package ru.isands.test.estore.model.entity.directory;

import lombok.Data;

import javax.persistence.*;

/**
 * Магазин
 */
@Entity
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
    @TableGenerator(name = "id_generator", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_value", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "address", nullable = false, columnDefinition = "text")
    private String address;
}
