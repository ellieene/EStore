package ru.isands.test.estore.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isands.test.estore.model.entity.ElectroShop;
import ru.isands.test.estore.model.entity.ElectroShopId;

import java.util.List;

@Repository
public interface ElectroShopRepository extends JpaRepository<ElectroShop, ElectroShopId> {
    List<ElectroShop> findByShopId(Long shopId, PageRequest pageRequest);
}
