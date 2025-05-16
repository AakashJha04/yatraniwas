package com.example.yatraniwas.repository;

import com.example.yatraniwas.dto.HotelDto;
import com.example.yatraniwas.entity.Hotel;
import com.example.yatraniwas.entity.Inventory;
import com.example.yatraniwas.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByRoom(Room room);

    @Query(
            """
                    SELECT DISTINCT i.hotel
                    FROM Inventory i
                    WHERE i.city = :city
                        AND i.date BETWEEN :startDate AND :endDate
                        AND i.closed = false
                        AND (i.totalCount - i.bookCount) >= :roomCount
                    GROUP BY i.hotel, i.room
                    HAVING COUNT(i.date) = :dateCount
            """
    )
    Page<Hotel> findHotelswithAvailableInventory(
        @Param("city") String city,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("roomCount") Integer roomCount,
        @Param("dateCount") Long dateCount,
        Pageable pageable
    );

}
