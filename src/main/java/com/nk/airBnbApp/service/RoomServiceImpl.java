package com.nk.airBnbApp.service;

import com.nk.airBnbApp.dto.RoomDto;
import com.nk.airBnbApp.entity.Hotel;
import com.nk.airBnbApp.entity.Room;
import com.nk.airBnbApp.entity.User;
import com.nk.airBnbApp.exception.ResourceNotFoundException;
import com.nk.airBnbApp.exception.UnAuthorisedException;
import com.nk.airBnbApp.repository.HotelRepository;
import com.nk.airBnbApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.nk.airBnbApp.util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements  RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;


    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in hotel with id {}", hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id : "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own this hotel with id : "+hotelId);
        }

        Room room=modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room=roomRepository.save(room);

        //create inventory as soon as room is created and if hotel is active
        if (hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all rooms in hotel with id {}", hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id : "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own this hotel with id : "+hotelId);
        }

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element,RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting room with id {}", roomId);
        Room room=roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with id : "+roomId));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting room with id {}", roomId);
        Room room=roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with id : "+roomId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(room.getHotel().getOwner())){
            throw new UnAuthorisedException("This user does not own this room with id : "+roomId);
        }

        // delete all future inventory for this room
        inventoryService.deleteAllInventories(room);

        roomRepository.deleteById(roomId);

    }

    @Override
    @Transactional
    public RoomDto updateRoomById(Long hotelId, Long roomId, RoomDto roomDto) {
        log.info("Updating the room with id : {}", roomId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id : "+hotelId));

        User user = getCurrentUser();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own this hotel with id : "+hotelId);
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with id : "+roomId));

        modelMapper.map(roomDto,room);
        room.setId(roomId);

        //TODO: if price or inventory is updated, then update the inventory for this room
        room=roomRepository.save(room);

        return modelMapper.map(room,RoomDto.class);
    }
}
