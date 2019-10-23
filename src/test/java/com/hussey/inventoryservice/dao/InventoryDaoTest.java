package com.hussey.inventoryservice.dao;
import com.hussey.inventoryservice.model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class InventoryDaoTest {
    // Autowired Dao
    @Autowired
    private InventoryDao inventoryDao;

    // setUp()
    @BeforeEach
    public void setUp() throws Exception {
        // Clean up the Inventory table in the database
        List<Inventory> inventoryList = inventoryDao.getAllInventory();
        inventoryList.forEach(inventory -> inventoryDao.deleteInventory(inventory.getInventoryId()));
    }

    @Test
    public void addInventory() {
        // Add a new Inventory Object to the database
        Inventory inventory = new Inventory(1, 10);
        inventory = inventoryDao.addInventory(inventory);

        // Create a copy of the newly added Inventory Object
        Inventory inventoryCopy = inventoryDao.getInventory(inventory.getInventoryId());

        // Test addInventory() method
        assertEquals(inventory, inventoryCopy);
    }

    @Test
    public void getInventory() {
        // Add a new Inventory Object to the database
        Inventory inventory = new Inventory(1, 10);
        inventory = inventoryDao.addInventory(inventory);

        // Create a copy of the newly added Inventory Object
        Inventory inventoryCopy = inventoryDao.getInventory(inventory.getInventoryId());

        // Test getInventory() method
        assertEquals(inventory, inventoryCopy);
    }

    @Test
    public void getAllInventory() {
        // Add 2 inventory objects to the database
        Inventory inventory1 = new Inventory(1, 10);
        Inventory inventory2 = new Inventory(1, 20);
        inventory1 = inventoryDao.addInventory(inventory1);
        inventory2 = inventoryDao.addInventory(inventory2);

        // Get a list of all the inventory items in the database
        List<Inventory> inventoryList = inventoryDao.getAllInventory();

        // Test getAllInventory() method
        assertEquals(2, inventoryList.size());
    }

    @Test
    public void getInventoryByProductId() {
        // Add 3 inventory objects to the database
        Inventory inventory1 = new Inventory(1, 10);
        Inventory inventory2 = new Inventory(1, 20);
        Inventory inventory3 = new Inventory(2, 30);
        inventory1 = inventoryDao.addInventory(inventory1);
        inventory2 = inventoryDao.addInventory(inventory2);
        inventory3 = inventoryDao.addInventory(inventory3);

        // Get a list of the inventory items by product id
        List<Inventory> productId1InventoryList = inventoryDao.getInventoryByProductId(1);
        List<Inventory> productId2InventoryList = inventoryDao.getInventoryByProductId(2);

        // Test getInventoryListByProductId() method
        assertEquals(2, productId1InventoryList.size());
        assertEquals(1, productId2InventoryList.size());
    }

    @Test
    public void updateInventory() {
        // Add an inventory object to the database
        Inventory inventory = new Inventory(1, 10);
        inventory = inventoryDao.addInventory(inventory);

        // Update the inventory object in the database
        inventory.setProductId(2);
        inventory.setQuantity(20);
        inventoryDao.updateInventory(inventory);

        // Get a copy of the updated inventory item
        Inventory inventoryCopy = inventoryDao.getInventory(inventory.getInventoryId());

        // Test updateInventory() method
        assertEquals(inventory, inventoryCopy);
    }

    @Test
    public void deleteInventory() {
        // Add an inventory object to the database
        Inventory inventory = new Inventory(1, 10);
        inventory = inventoryDao.addInventory(inventory);

        // Delete the inventory item in the database
        inventoryDao.deleteInventory(inventory.getInventoryId());

        // Get a copy of the deleted inventory object
        Inventory inventoryCopy = inventoryDao.getInventory(inventory.getInventoryId());

        // Test deleteInventory() method
        assertNull(inventoryCopy);

    }
}
