package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.BookingDto;
import com.example.yatraniwas.dto.BookingRequest;
import com.example.yatraniwas.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
