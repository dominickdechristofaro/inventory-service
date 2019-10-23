package com.hussey.inventoryservice.service;
import com.hussey.inventoryservice.model.Inventory;
import java.util.List;

public interface InventoryService {
    Inventory addInventory(Inventory inventory);
    Inventory getInventory(int inventoryId);
    List<Inventory> getAllInventory();
    List<Inventory> getInventoryByProductId(int productId);
    void updateInventory(Inventory inventory);
    void deleteInventory(int inventoryId);
}
