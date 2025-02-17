package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isands.test.estore.model.entity.directory.Position;

import java.util.Optional;

@Repository
public interface JobTitleRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByName(String name);
}
