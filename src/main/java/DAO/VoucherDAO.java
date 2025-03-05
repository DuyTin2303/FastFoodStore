package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Vouchers;

public class VoucherDAO extends DBContext {

    public List<Vouchers> getAllValid() {
        List<Vouchers> list = new ArrayList<>();
        String query = "SELECT *\n"
                + "FROM Vouchers\n"
                + "WHERE GETDATE() BETWEEN valid_from AND valid_until\n"
                + "  AND is_used = 0;";
        try {
            ResultSet rs = execSelectQuery(query);
            while (rs.next()) {
                list.add(new Vouchers(rs.getInt("voucher_id"),
                        rs.getString("name"),
                        rs.getDouble("discount_percentage"),
                        rs.getDate("valid_from").toLocalDate(),
                        rs.getDate("valid_until").toLocalDate(),
                        rs.getInt("so_luong"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Vouchers getById(int id) {
        String query = "SELECT * FROM Vouchers WHERE (voucher_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, id);
            if (rs.next()) {
                return new Vouchers(rs.getInt("voucher_id"),
                        rs.getString("name"),
                        rs.getDouble("discount_percentage"),
                        rs.getDate("valid_from").toLocalDate(),
                        rs.getDate("valid_until").toLocalDate(),
                        rs.getInt("so_luong"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (Exception e) {
        }
        return null;
    }
}
