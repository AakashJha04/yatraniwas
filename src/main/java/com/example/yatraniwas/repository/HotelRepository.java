package com.example.yatraniwas.repository;

import com.example.yatraniwas.entity.Hotel;
import com.example.yatraniwas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByOwner(User user);
}
