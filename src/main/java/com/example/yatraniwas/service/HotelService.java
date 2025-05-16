package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.HotelDto;
import com.example.yatraniwas.entity.Hotel;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateHotelById(Long id, HotelDto hotelDto);
    void deleteHotelById(Long id);
    void activeHotel(Long id);
}
