package ru.isands.test.estore.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.isands.test.estore.model.entity.directory.ElectroType;
import ru.isands.test.estore.model.entity.directory.Position;
import ru.isands.test.estore.model.entity.directory.Shop;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
    @TableGenerator(name = "id_generator", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_value", allocationSize = 1)
    @Column(name = "employee_id")
    private long id;

    @NotNull
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "patronymic", length = 100)
    private String patronymic;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @NotNull
    @Column(nullable = false)
    private Boolean gender;

    @ManyToMany
    @JoinTable(
            name = "electro_employee",
            joinColumns = @JoinColumn(name = "employee_employee_id"),
            inverseJoinColumns = @JoinColumn(name = "electro_type_id")
    )
    private List<ElectroType> electroTypes;

}
