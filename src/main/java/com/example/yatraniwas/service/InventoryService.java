package com.example.yatraniwas.service;

import com.example.yatraniwas.entity.Room;

public interface InventoryService {

    void initializeRoomForYear(Room room);
    void deleteFutureInventories(Room room);

}
