package com.example.yatraniwas.Controller;

import com.example.yatraniwas.dto.HotelDto;
import com.example.yatraniwas.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        log.info("[INFO]:\tController to create new Hotel with name: {}", hotelDto.getName());
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        log.info("[INFO]:\tHotel created successfully with name: {}", hotelDto.getName());
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId){
        log.info("[INFO]:\tController to get Hotel with id: {}", hotelId);
        HotelDto hotel = hotelService.getHotelById(hotelId);
        log.info("[INFO]:\tHotel Got successfully with id: {}", hotelId);
        return ResponseEntity.ok(hotel);
    }


    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId, @RequestBody HotelDto hotelDto){
        log.info("[INFO]:\tController to modify Hotel with id: {}", hotelId);
        HotelDto hotel = hotelService.updateHotelById(hotelId, hotelDto);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId){
        log.info("[INFO]:\tController to delete Hotel with id: {}", hotelId);
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelId}/activate")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId){
        log.info("[INFO]:\tController to get Hotel with id: {}", hotelId);
        hotelService.activeHotel(hotelId);
        return ResponseEntity.noContent().build();
    }




}
