package model;

import java.util.UUID;

public class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private  int quantity;
    public Product() {
    }
    public Product(String name, String category, double price, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %.2f$, Кол-во: %d", id, name, category, price, quantity);
    }
}
