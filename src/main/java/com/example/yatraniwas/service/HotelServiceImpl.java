package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.HotelDto;
import com.example.yatraniwas.entity.Hotel;
import com.example.yatraniwas.exception.ResourceNotFoundException;
import com.example.yatraniwas.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
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
    public void deleteHotelById(Long id) {
        log.info("[INFO]:\tChecking if the Hotel with ID {} } exists or not", id);
        boolean exists = hotelRepository.existsById(id);
        if(!exists){
            throw new ResourceNotFoundException("Hotel not found with id "+id);
        }
        log.info("[INFO]:\tDeleting the Hotel with id: {}", id);
        hotelRepository.deleteById(id);
        // delete the future inventory for this hotel;
    }

}
