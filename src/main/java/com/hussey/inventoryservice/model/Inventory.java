package com.hussey.inventoryservice.model;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class Inventory {
    // Properties
    private Integer inventoryId;

    @NotNull(message = "productId must not be null.")
    @Positive(message = "productId must be a positive number between 1 and 2,147,483,647.")
    private Integer productId;

    @NotNull(message = "quantity must not be null.")
    @PositiveOrZero(message = "quantity must be a positive integer number between 0 and 2,147,483,647.")
    private Integer quantity;

    // Constructors
    public Inventory() {

    }

    public Inventory(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Inventory(int inventoryId, int productId, int quantity) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters
    public Integer getInventoryId() {
        return this.inventoryId;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    // Setters
    public void setInventoryId(Integer inventoryIdIn) {
        this.inventoryId = inventoryIdIn;
    }

    public void setProductId(Integer productIdIn) {
        this.productId = productIdIn;
    }

    public void setQuantity(Integer quantityIn) {
        this.quantity = quantityIn;
    }

    // equals(), hashCode(), and toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(inventoryId, inventory.inventoryId) &&
                productId.equals(inventory.productId) &&
                quantity.equals(inventory.quantity);
    }
}
