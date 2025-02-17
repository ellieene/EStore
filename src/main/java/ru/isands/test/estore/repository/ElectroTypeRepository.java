package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isands.test.estore.model.entity.directory.ElectroType;

import java.util.Optional;

@Repository
public interface ElectronicsTypeRepository extends JpaRepository<ElectroType, Long> {
    Optional<ElectroType> findByName(String name);
}
