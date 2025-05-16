package com.example.yatraniwas.dto;
import com.example.yatraniwas.entity.HotelContactInfo;
import lombok.Data;
@Data
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo hotelContactInfo;
    private Boolean active;
}
