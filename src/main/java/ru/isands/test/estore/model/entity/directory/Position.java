package ru.isands.test.estore.model.entity.directory;

import lombok.Data;

import javax.persistence.*;


/**
 * Должность
 */
@Data
@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
    @TableGenerator(name = "id_generator", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_value", allocationSize = 1)
    private Long id;

    private String name;
}
