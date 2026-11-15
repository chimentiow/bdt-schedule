package bdt.dao;

import bdt.db.Database;
import bdt.model.Room;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Каталог приміщень (Pure Fabrication). */
public class RoomCatalog implements RoomSource {
    private final Database db;

    public RoomCatalog(Database db) {
        this.db = db;
    }

    @Override
    public List<Room> findAll() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT room_id, room_number, room_type, capacity FROM room ORDER BY room_number";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRoom(rs));
            }
        } catch (SQLException e) {
            throw new DataException("Помилка читання приміщень", e);
        }
        return list;
    }

    @Override
    public Room findRoom(int roomId) {
        String sql = "SELECT room_id, room_number, room_type, capacity FROM room WHERE room_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRoom(rs) : null;
            }
        } catch (SQLException e) {
            throw new DataException("Помилка читання приміщення", e);
        }
    }

    static Room mapRoom(ResultSet rs) throws SQLException {
        return new Room(rs.getInt("room_id"), rs.getString("room_number"), rs.getString("room_type"),
                rs.getInt("capacity"));
    }
}
