package com.example.yatraniwas.repository;

import com.example.yatraniwas.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
