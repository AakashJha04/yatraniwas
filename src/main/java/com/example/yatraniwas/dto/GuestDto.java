package com.example.yatraniwas.dto;

import com.example.yatraniwas.entity.User;
import com.example.yatraniwas.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
