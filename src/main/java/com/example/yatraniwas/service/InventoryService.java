package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.HotelDto;
import com.example.yatraniwas.dto.HotelPriceDto;
import com.example.yatraniwas.dto.HotelSearchRequest;
import com.example.yatraniwas.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForYear(Room room);
    void deleteAllInventories(Room room);
    Page<HotelPriceDto>  searchHotels(HotelSearchRequest hotelSearchRequest);

}
