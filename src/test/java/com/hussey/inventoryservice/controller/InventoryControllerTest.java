package com.hussey.inventoryservice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hussey.inventoryservice.model.Inventory;
import com.hussey.inventoryservice.service.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {
    // Properties
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InventoryServiceImpl inventoryService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArgumentCaptor inventoryArgumentCaptor = ArgumentCaptor.forClass(Inventory.class);

    // Tests
    @Test
    public void addInventoryShouldReturnCreatedInventory() throws Exception {
        // Create an inputInventory object
        Inventory inputInventory = new Inventory(1, 10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // Create a responseInventory object
        Inventory responseInventory = new Inventory(1, 1, 10);
        String responseJson = objectMapper.writeValueAsString(responseInventory);

        // Mock the inventoryService
        Mockito.when(inventoryService.addInventory(inputInventory)).thenReturn(responseInventory);

        // Mock the MVC
        this.mockMvc.perform(post("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void addInventoryMissingProductIdShouldReturn422() throws Exception {
        // Create an inputInventory object
        Inventory inputInventory = new Inventory();
        inputInventory.setQuantity(10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // Mock the MVC
        this.mockMvc.perform(post("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void addInventoryProductId0ShouldReturn422() throws Exception {
        // Create an inputInventory object
        Inventory inputInventory = new Inventory();
        inputInventory.setProductId(0);
        inputInventory.setQuantity(10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // Mock the MVC
        this.mockMvc.perform(post("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void addInventoryMissingQuantityShouldReturn422() throws Exception {
        // Create an inputInventory object
        Inventory inputInventory = new Inventory();
        inputInventory.setProductId(1);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // Mock the MVC
        this.mockMvc.perform(post("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void addInventoryQuantityNegative1ShouldReturn422() throws Exception {
        // Create an inputInventory object
        Inventory inputInventory = new Inventory();
        inputInventory.setProductId(1);
        inputInventory.setQuantity(-1);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // Mock the MVC
        this.mockMvc.perform(post("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getInventoryShouldReturnInventoryById() throws Exception {
        // Create an responseInventory Object
        Inventory responseInventory = new Inventory(1, 1, 10);
        String responseJson = objectMapper.writeValueAsString(responseInventory);

        // Mock the InventoryService
        Mockito.when(inventoryService.getInventory(1)).thenReturn(responseInventory);

        // Mock the MVC
        this.mockMvc.perform(get("/inventory/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void getInventoryWithInvalidInventoryIdShouldReturn404() throws Exception {
        // Mock inventoryService
        Mockito.when(inventoryService.getInventory(1)).thenReturn(null);

        // Mock the MVC
        this.mockMvc.perform(get("/inventory/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllInventoryShouldReturnInventoryList() throws Exception {
        // Build Inventory Objects
        Inventory inventory1 = new Inventory(1,1, 10);
        Inventory inventory2 = new Inventory(2, 1, 20);
        Inventory inventory3 = new Inventory(3, 2, 30);

        // Add Inventory to a List
        List<Inventory> allInventoryList = new ArrayList<>();
        allInventoryList.add(inventory1);
        allInventoryList.add(inventory2);
        allInventoryList.add(inventory3);
        String responseJson = objectMapper.writeValueAsString(allInventoryList);

        // Mock the InventoryService
        Mockito.when(inventoryService.getAllInventory()).thenReturn(allInventoryList);

        // Mock the MVC
        this.mockMvc.perform(get("/inventory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void getInventoryByProductIdShouldReturnInventoryListByProductId() throws Exception {
        // Build Inventory Objects
        Inventory inventory1 = new Inventory(1,1, 10);
        Inventory inventory2 = new Inventory(2, 1, 20);
        Inventory inventory3 = new Inventory(3, 2, 30);

        // Make ProductId Specific Inventory Lists
        List<Inventory> productId1InventoryList = new ArrayList<>();
        List<Inventory> productId2InventoryList = new ArrayList<>();
        productId1InventoryList.add(inventory1);
        productId1InventoryList.add(inventory2);
        productId2InventoryList.add(inventory3);

        // Convert the productId specific Inventory Lists to Json
        String productId1ResponseJson = objectMapper.writeValueAsString(productId1InventoryList);
        String productId2ResponseJson = objectMapper.writeValueAsString(productId2InventoryList);

        // Mock the InventoryService
        Mockito.when(inventoryService.getInventoryByProductId(1)).thenReturn(productId1InventoryList);
        Mockito.when(inventoryService.getInventoryByProductId(2)).thenReturn(productId2InventoryList);

        // Mock the MVC
        this.mockMvc.perform(get("/inventory/byProduct/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(productId1ResponseJson));

        this.mockMvc.perform(get("/inventory/byProduct/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(productId2ResponseJson));
    }

    @Test
    public void updateInventoryShouldReturn202() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory(1, 1, 10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // Mock the inventoryService
        Mockito.when(inventoryService.getInventory(1)).thenReturn(inputInventory);
        Mockito.doNothing().when(inventoryService).updateInventory(inputInventory);

        // MVC Mock
        this.mockMvc.perform(put("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void updateInventoryMissingInventoryIdShouldReturn404() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory(1, 10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // MVC Mock
        this.mockMvc.perform(put("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInventoryInvalidInventoryIdShouldReturn404() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory(0,1, 10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // Mock the inventoryService method
        Mockito.when(inventoryService.getInventory(0)).thenReturn(null);

        // MVC Mock
        this.mockMvc.perform(put("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInventoryMissingProductIdShouldReturn422() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory();
        inputInventory.setInventoryId(1);
        inputInventory.setQuantity(10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // MVC Mock
        this.mockMvc.perform(put("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateInventoryProductId0ShouldReturn422() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory(1, 0, 10);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // MVC Mock
        this.mockMvc.perform(put("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateInventoryMissingQuantityShouldReturn422() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory();
        inputInventory.setInventoryId(1);
        inputInventory.setProductId(11);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // MVC Mock
        this.mockMvc.perform(put("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateInventoryNegativeQuantityShouldReturn422() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory(1, 1, -1);
        String inputJson = objectMapper.writeValueAsString(inputInventory);

        // MVC Mock
        this.mockMvc.perform(put("/inventory")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void deleteInventoryShouldReturn202() throws Exception {
        // Create inputInventory object
        Inventory inputInventory = new Inventory(1, 1, 10);

        // Mock the inventoryService
        Mockito.when(inventoryService.getInventory(1)).thenReturn(inputInventory);

        // Mock the MVC
        this.mockMvc.perform(delete("/inventory/1"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void deletePostWithInvalidInventoryIdShouldReturn404() throws Exception {
        // Mock the inventoryService
        Mockito.when(inventoryService.getInventory(1)).thenReturn(null);

        // Mock the MVC
        this.mockMvc.perform(delete("/inventory/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
