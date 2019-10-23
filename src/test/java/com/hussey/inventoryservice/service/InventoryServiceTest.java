package com.hussey.inventoryservice.service;
import com.hussey.inventoryservice.dao.InventoryDao;
import com.hussey.inventoryservice.dao.InventoryDaoJdbcTemplateImpl;
import com.hussey.inventoryservice.model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

public class InventoryServiceTest {
    // Properties
    private InventoryServiceImpl inventoryService;
    private ArgumentCaptor<Inventory> inventoryArgumentCaptor = ArgumentCaptor.forClass(Inventory.class);
    private ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
    private InventoryDao inventoryDao;

    @BeforeEach
    public void setUp() throws Exception {
        setUpInventoryDaoMock();
        inventoryService = new InventoryServiceImpl(inventoryDao);
    }

    @Test
    public void addInventory() {
        // Add Inventory to the database
        Inventory inventoryInput = new Inventory(1, 10);
        Inventory inventory = inventoryService.addInventory(inventoryInput);

        // Copy the Inventory object added to the database
        Inventory inventoryCopy = inventoryService.getInventory(inventory.getInventoryId());

        // Test the addInventory() method
        Mockito.verify(inventoryDao, times(1)).getInventory(inventory.getInventoryId());
        assertEquals(inventory, inventoryCopy);
    }

    @Test
    public void getInventory() {
        // Add Inventory to the database
        Inventory inventoryInput = new Inventory(1, 10);
        Inventory inventory = inventoryService.addInventory(inventoryInput);

        // Copy the Inventory object added to the database
        Inventory inventoryCopy = inventoryService.getInventory(inventory.getInventoryId());

        // Test the addInventory() method
        Mockito.verify(inventoryDao, times(1)).getInventory(inventory.getInventoryId());
        assertEquals(inventory, inventoryCopy);
    }

    @Test
    public void getAllInventory() {
        // Get a list of all Inventory in the database
        List<Inventory> allInventoryList = inventoryService.getAllInventory();

        // Test the getAllInventory() API method
        assertEquals(3, allInventoryList.size());
    }

    @Test
    public void getInventoryByProductId() {
        // Get a list of all Inventory in the database with a Product ID #1
        List<Inventory> productId1InventoryList = inventoryService.getInventoryByProductId(1);
        List<Inventory> productId2InventoryList = inventoryService.getInventoryByProductId(2);

        // Test the getInventoryByProductId() method
        assertEquals(2, productId1InventoryList.size());
        assertEquals(1, productId2InventoryList.size());
    }

    @Test
    public void updateInventory() {
        // Add an Inventory to the database
        Inventory inventoryInput = new Inventory(1, 10);
        Inventory inventory = inventoryService.addInventory(inventoryInput);

        // Update the Inventory in the database
        inventory.setProductId(2);
        inventoryService.updateInventory(inventory);

        // Test the updateInventory() method
        Mockito.verify(inventoryDao, times(1)).updateInventory(inventoryArgumentCaptor.getValue());
        assertEquals(inventory, inventoryArgumentCaptor.getValue());
    }

    @Test
    public void deleteInventory() {
        // Add an Inventory to the database
        Inventory inventoryInput = new Inventory(1, 10);
        Inventory inventory = inventoryService.addInventory(inventoryInput);

        // Delete the inventory from the database
        inventoryService.deleteInventory(inventory.getInventoryId());

        // Test the deleteInventory() method
        Mockito.verify(inventoryDao, times(1)).deleteInventory(integerArgumentCaptor.getValue());
        assertEquals(inventory.getInventoryId(), integerArgumentCaptor.getValue());
    }

    // InventoryDao Mock
    private void setUpInventoryDaoMock() {
        // Set up the InventoryDao Mock
        inventoryDao = Mockito.mock(InventoryDaoJdbcTemplateImpl.class);

        // Set up Inventory Input & Responses
        Inventory inventoryInput1 = new Inventory(1, 10);
        Inventory inventoryInput2 = new Inventory(1, 20);
        Inventory inventoryInput3 = new Inventory(2, 30);
        Inventory inventoryResponse1 = new Inventory(1, 1, 10);
        Inventory inventoryResponse2 = new Inventory(2, 1, 20);
        Inventory inventoryResponse3 = new Inventory(3, 2, 30);

        // Set up all Item Inventory List
        List<Inventory> allInventoryList = new ArrayList<>();
        allInventoryList.add(inventoryResponse1);
        allInventoryList.add(inventoryResponse2);
        allInventoryList.add(inventoryResponse3);

        // Set up Product ID #1 specific Inventory List
        List<Inventory> productId1InventoryList = new ArrayList<>();
        productId1InventoryList.add(inventoryResponse1);
        productId1InventoryList.add(inventoryResponse2);

        // Set up Product ID #2 specific Inventory List
        List<Inventory> productId2InventoryList = new ArrayList<>();
        productId2InventoryList.add(inventoryResponse3);

        // Mocking addInventory() DAO method
        Mockito.doReturn(inventoryResponse1).when(inventoryDao).addInventory(inventoryInput1);
        Mockito.doReturn(inventoryResponse2).when(inventoryDao).addInventory(inventoryInput2);
        Mockito.doReturn(inventoryResponse3).when(inventoryDao).addInventory(inventoryInput3);

        // Mocking getInventory() DAO method
        Mockito.doReturn(inventoryResponse1).when(inventoryDao).getInventory(1);
        Mockito.doReturn(inventoryResponse2).when(inventoryDao).getInventory(2);
        Mockito.doReturn(inventoryResponse3).when(inventoryDao).getInventory(3);

        // Mocking getAllInventory() DAO method
        Mockito.doReturn(allInventoryList).when(inventoryDao).getAllInventory();

        // Mocking getInventoryByProductId() DAO method
        Mockito.doReturn(productId1InventoryList).when(inventoryDao).getInventoryByProductId(1);
        Mockito.doReturn(productId2InventoryList).when(inventoryDao).getInventoryByProductId(2);

        // Mocking updateInventory() DAO method
        Mockito.doNothing().when(inventoryDao).updateInventory(inventoryArgumentCaptor.capture());

        // Mocking deleteInventory() DAO method
        Mockito.doNothing().when(inventoryDao).deleteInventory(integerArgumentCaptor.capture());
    }
}
