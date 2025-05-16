package com.example.yatraniwas.repository;

import com.example.yatraniwas.entity.Inventory;
import com.example.yatraniwas.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByRoom(Room room);
}
