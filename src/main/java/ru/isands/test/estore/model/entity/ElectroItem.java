package ru.isands.test.estore.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.isands.test.estore.model.entity.directory.ElectroType;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class ElectroItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "etype_id")
    private ElectroType type;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "archive")
    private boolean archive;

    @Column(name = "text", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "electroItem", cascade = CascadeType.ALL)
    private List<ElectroShop> electroShops;

    public void updateTotalCount() {
        this.count = electroShops.stream().mapToInt(ElectroShop::getCount).sum();
    }

}
