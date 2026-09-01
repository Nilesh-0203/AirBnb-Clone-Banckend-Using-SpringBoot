package com.nk.airBnbApp.dto;

import com.nk.airBnbApp.entity.Hotel;
import com.nk.airBnbApp.entity.Room;
import com.nk.airBnbApp.entity.User;
import com.nk.airBnbApp.entity.enums.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guest;
    private BigDecimal amount;
}
