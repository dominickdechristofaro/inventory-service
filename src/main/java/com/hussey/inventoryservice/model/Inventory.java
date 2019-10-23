package com.hussey.inventoryservice.model;
import java.util.Objects;

public class Inventory {
    // Properties
    private int inventoryId;
    private int productId;
    private int quantity;

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
    public int getInventoryId() {
        return this.inventoryId;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    // Setters
    public void setInventoryId(int inventoryIdIn) {
        this.inventoryId = inventoryIdIn;
    }

    public void setProductId(int productIdIn) {
        this.productId = productIdIn;
    }

    public void setQuantity(int quantityIn) {
        this.quantity = quantityIn;
    }

    // equals(), hashCode(), and toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return inventoryId == inventory.inventoryId &&
                productId == inventory.productId &&
                quantity == inventory.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, productId, quantity);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
