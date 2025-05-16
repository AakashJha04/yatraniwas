package com.example.yatraniwas.repository;

import com.example.yatraniwas.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
