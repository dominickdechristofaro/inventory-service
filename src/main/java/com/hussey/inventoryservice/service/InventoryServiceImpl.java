package com.hussey.inventoryservice.service;
import com.hussey.inventoryservice.dao.InventoryDao;
import com.hussey.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class InventoryServiceImpl implements InventoryService {
    // Properties
    private InventoryDao inventoryDao;

    // Constructor
    @Autowired
    public InventoryServiceImpl(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    // Methods
    @Override
    @Transactional
    public Inventory addInventory(Inventory inventory) {
        return inventoryDao.addInventory(inventory);
    }

    @Override
    public Inventory getInventory(int inventoryId) {
        return inventoryDao.getInventory(inventoryId);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryDao.getAllInventory();
    }

    @Override
    public List<Inventory> getInventoryByProductId(int productId) {
        return inventoryDao.getInventoryByProductId(productId);
    }

    @Override
    @Transactional
    public void updateInventory(Inventory inventory) {
        inventoryDao.updateInventory(inventory);
    }

    @Override
    @Transactional
    public void deleteInventory(int inventoryId) {
        inventoryDao.deleteInventory(inventoryId);
    }
}
