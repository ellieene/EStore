package ru.isands.test.estore.model.entity.directory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.isands.test.estore.model.entity.Employee;

import javax.persistence.*;
import java.util.Set;

/**
 * Тип электроники
 */
@Data
@Entity
public class ElectroType {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
    @TableGenerator(name = "id_generator", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_value", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "electroTypes")
    private Set<Employee> employees;

}
