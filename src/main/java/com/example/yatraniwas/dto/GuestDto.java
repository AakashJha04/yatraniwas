package com.example.yatraniwas.dto;

import com.example.yatraniwas.entity.User;
import com.example.yatraniwas.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestDto {
    private Long id;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
}
