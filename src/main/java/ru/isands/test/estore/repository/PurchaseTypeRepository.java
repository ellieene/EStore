package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isands.test.estore.model.entity.directory.PurchaseType;

import java.util.Optional;

@Repository
public interface PurchaseTypeRepository extends JpaRepository<PurchaseType, Long> {
    Optional<PurchaseType> findByName(String name);
}
