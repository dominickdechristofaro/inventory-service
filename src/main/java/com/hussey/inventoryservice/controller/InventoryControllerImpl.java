package com.hussey.inventoryservice.controller;
import com.hussey.inventoryservice.model.Inventory;
import com.hussey.inventoryservice.service.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class InventoryControllerImpl implements InventoryController {
    // Properties
    @Autowired
    private InventoryServiceImpl inventoryService;

    // Constructor
    public InventoryControllerImpl() {

    }

    @Override
    @Transactional
    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Inventory addInventory(@RequestBody @Valid Inventory inventory) {
        return inventoryService.addInventory(inventory);
    }

    @Override
    @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Inventory getInventory(@PathVariable int inventoryId) {
        // Get the requested Inventory from the database
        Inventory inventoryFromDB = inventoryService.getInventory(inventoryId);

        // Return the Inventory if it is found in the database
        if(inventoryFromDB != null) {
            return inventoryFromDB;
        } else {
            throw new IllegalArgumentException("There is no Inventory with the id: " + inventoryId);
        }
    }

    @Override
    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Inventory> getAllInventory() {
        // Get all Inventory in the database
        return inventoryService.getAllInventory();
    }

    @Override
    @RequestMapping(value = "/inventory/byProduct/{productId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Inventory> getInventoryByProductId(@PathVariable int productId) {
        // Get all Inventory in the database by a specific productId
        return inventoryService.getInventoryByProductId(productId);
    }

    @Override
    @Transactional
    @RequestMapping(value = "/inventory", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateInventory(@RequestBody @Valid Inventory inventory) {
        // Validate that Inventory object has an Id
        if(inventory.getInventoryId() == null) {
            throw new IllegalArgumentException("You must pass an inventoryId when trying to update.");
        }

        // Get the Inventory object from the database
        Inventory inventoryFromDB = inventoryService.getInventory(inventory.getInventoryId());

        // Update the Inventory if it is found in the database
        if(inventoryFromDB != null) {
            inventoryService.updateInventory(inventory);
        } else {
            throw new IllegalArgumentException("There is no Inventory with id: " + inventory.getInventoryId());
        }
    }

    @Override
    @Transactional
    @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deleteInventory(@PathVariable int inventoryId) {
        // Get the Inventory from the database
        Inventory inventoryFromDB = inventoryService.getInventory(inventoryId);

        // Delete the Inventory if it is found in the database
        if (inventoryFromDB != null) {
            inventoryService.deleteInventory(inventoryId);
        } else {
            throw new IllegalArgumentException("There is no Inventory with id: " + inventoryId);
        }
    }
}
