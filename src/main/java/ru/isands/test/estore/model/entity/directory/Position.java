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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;
}
