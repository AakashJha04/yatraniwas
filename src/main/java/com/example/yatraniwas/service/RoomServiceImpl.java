package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.RoomDto;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelmapper;

    @Override
    @Transactional
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("[INFO]:\tGetting the Hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(()->new ResourceNotFoundException("Hotel not found with id "+hotelId));
        log.info("Creating a new Room in hotel of id: "+hotelId);
        Room room = modelmapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        // DONE ---------- created inventory for Room ----- DONE
        if(hotel.getActive()){
            inventoryService.initializeRoomForYear(room);
        }
        return modelmapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("[INFO]:\tGetting all rooms in hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(()->new ResourceNotFoundException("Hotel not found with id "+hotelId));
        return hotel.getRooms().
                stream()
                .map((element) -> modelmapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("[INFO]:\tGetting the room with ID: {}", roomId);
        Room room = roomRepository.
                findById(roomId).
                orElseThrow(()->new ResourceNotFoundException("Room not found with id "+roomId));
        return modelmapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("[INFO]:\tChecking the room with ID: {} is present", roomId);
        Room room = roomRepository.
                findById(roomId).
                orElseThrow(()->new ResourceNotFoundException("Room not found with id "+roomId));
        // DONE --------- delete all future inventory ------ DONE
        inventoryService.deleteFutureInventories(room);
        roomRepository.deleteById(roomId);
    }
}
