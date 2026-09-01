package com.nk.airBnbApp.dto;

import com.nk.airBnbApp.entity.User;
import com.nk.airBnbApp.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Integer age;
    private Gender gender;
}
