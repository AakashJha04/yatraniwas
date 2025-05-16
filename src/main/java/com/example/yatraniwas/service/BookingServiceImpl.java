package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.BookingDto;
import com.example.yatraniwas.dto.BookingRequest;
import com.example.yatraniwas.dto.GuestDto;
import com.example.yatraniwas.entity.*;
import com.example.yatraniwas.entity.enums.BookingStatus;
import com.example.yatraniwas.exception.ResourceNotFoundException;
import com.example.yatraniwas.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        log.info("Initialising Booking.......");
        Hotel hotel = hotelRepository
                .findById(bookingRequest.getHotelId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Hotel not found with id: "+ bookingRequest.getHotelId()));
        Room room = roomRepository
                .findById(bookingRequest.getRoomId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Room not found with id: "+ bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),
                bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())+1;
        if (inventoryList.size()!=daysCount){
            throw new IllegalStateException("Sorry! Room is not available anymore");
        }

        // reserve the room
        log.info("reserve room.......");

        for(Inventory inventory:inventoryList){
            inventory.setReservedCount(inventory.getReservedCount()+bookingRequest.getRoomCount());
        }

        inventoryRepository.saveAll(inventoryList);

        // create the booking
        log.info("create the booking ......");

        // TODO: calculated dynamic price amount

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding Guests.......");
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found with id: "+ bookingId));

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking Timeout");
        }
        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
        }
        for(GuestDto guestDto: guestDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        User temp_user = new User(); // remove it after spring security added
        temp_user.setId(1L);
        return temp_user;
    }


}
