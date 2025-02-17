package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isands.test.estore.model.entity.ElectroItem;

import java.util.Optional;


@Repository
public interface ElectroItemRepository extends JpaRepository<ElectroItem, Long> {
    Optional<ElectroItem> findByName(String name);
}
