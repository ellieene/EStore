package ru.isands.test.estore.model.entity.directory;

import lombok.Data;

import javax.persistence.*;

/**
 * Тип электроники
 */
@Data
@Entity
public class ElectroType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

}
