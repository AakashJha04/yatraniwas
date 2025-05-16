package com.example.yatraniwas.repository;

import com.example.yatraniwas.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
