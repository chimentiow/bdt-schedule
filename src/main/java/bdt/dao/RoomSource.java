package bdt.dao;

import bdt.model.Room;
import java.util.List;

/** Джерело даних про приміщення. */
public interface RoomSource {
    List<Room> findAll();

    Room findRoom(int roomId);
}
