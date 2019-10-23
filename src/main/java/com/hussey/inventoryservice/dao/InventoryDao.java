package com.hussey.inventoryservice.dao;
import com.hussey.inventoryservice.model.Inventory;
import java.util.List;

public interface InventoryDao {
    Inventory addInventory(Inventory inventory);
    Inventory getInventory(int inventoryId);
    List<Inventory> getAllInventory();
    List<Inventory> getInventoryByProductId(int productId);
    void updateInventory(Inventory inventory);
    void deleteInventory(int inventoryId);
}
