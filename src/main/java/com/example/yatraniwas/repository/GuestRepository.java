package com.example.yatraniwas.repository;

import com.example.yatraniwas.entity.Guest;
import com.example.yatraniwas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByUser(User user);
}