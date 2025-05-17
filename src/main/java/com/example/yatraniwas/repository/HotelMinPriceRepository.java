package com.example.yatraniwas.repository;
import com.example.yatraniwas.dto.HotelPriceDto;
import com.example.yatraniwas.entity.Hotel;
import com.example.yatraniwas.entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long> {

    @Query(
            """
                    SELECT com.example.yatraniwas.dto.HotelPriceDto(i.hotel, AVG(i.price))
                    FROM HotelMinPrice i
                    WHERE i.hotel.city = :city
                        AND i.date BETWEEN :startDate AND :endDate
                        AND i.hotel.active = true
                    GROUP BY i.hotel
            """
    )
    Page<HotelPriceDto> findHotelswithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
