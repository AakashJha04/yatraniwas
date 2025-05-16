package com.example.yatraniwas.dto;
import com.example.yatraniwas.entity.Guest;
import com.example.yatraniwas.entity.Hotel;
import com.example.yatraniwas.entity.Room;
import com.example.yatraniwas.entity.User;
import com.example.yatraniwas.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
//    private Hotel hotel;
//    private Room room;
    private User user;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
