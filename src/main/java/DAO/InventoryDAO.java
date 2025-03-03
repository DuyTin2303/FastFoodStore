package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class InventoryDAO extends DBContext {

    public int add(int dishId, int supplier_id, int quantity, double purchasePrice, double sellingPrice) {
        String query = "INSERT INTO Inventory (dish_id, supplier_id, quantity, purchase_price, selling_price, created_at)\n"
                + "OUTPUT inserted.inventory_id\n"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ResultSet rs = execSelectQuery(query, dishId,
                    supplier_id,
                    quantity,
                    purchasePrice,
                    sellingPrice,
                    LocalDateTime.now());
            if (rs.next()) {
                return rs.getInt("inventory_id");
            }
        } catch (Exception e) {
        }
        return 0;
    }

}
