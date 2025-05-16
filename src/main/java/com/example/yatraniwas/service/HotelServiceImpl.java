package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.HotelDto;
import com.example.yatraniwas.entity.Hotel;
import com.example.yatraniwas.entity.Room;
import com.example.yatraniwas.exception.ResourceNotFoundException;
import com.example.yatraniwas.repository.HotelRepository;
import com.example.yatraniwas.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("[INFO]:\tCreating a new Hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("[INFO]:\tCreated a new Hotel with id: {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("[INFO]:\tGetting the Hotel with ID: {}", id);
        Hotel hotel = hotelRepository.
                findById(id).
                orElseThrow(()->new ResourceNotFoundException("Hotel not found with id "+id));
        log.info("[INFO]:\tGot Hotel with id: {}", id);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("[INFO]:\tgetting the Hotel with ID: {}", id);
        Hotel hotel = hotelRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Hotel not found with id"+id));
        log.info("[INFO]:\tgot Hotel with id: {}", id);
        log.info("[INFO]:\tupdating the Hotel with ID: {}", id);
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        log.info("[INFO]:\tDeleting the Hotel with id: {}", id);
        Hotel hotel = hotelRepository.
                findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not present with id: "+ id));
        // deleted the future inventory for this hotel;
        for(Room room: hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activeHotel(Long hotelId) {
        log.info("[INFO]:\tActivating the Hotel with ID {}: ", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not present with id: "+ hotelId));
        hotel.setActive(true);

        // DONE ---------- create inventory for all the rooms for this hotel -------------- DONE
        // assuming one doing once
        for(Room room: hotel.getRooms()){
            inventoryService.initializeRoomForYear(room);
        }
    }


}
