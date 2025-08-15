package service;

import model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryService {
    private final Map<String, Product> inventory;

    public InventoryService(Map<String, Product> initialProducts) {
        this.inventory = new HashMap<>(initialProducts);
    }

    public InventoryService() {
        this.inventory = new HashMap<>();
    }


    public Product addProduct(String name, String category, double price, int quantity) {
        Product product = new Product(name, category, price, quantity);
        inventory.put(product.getId(), product);
        return product;
    }

    public boolean removeProduct(Product product) {
        return inventory.remove(product.getId()) != null;
    }

    public Product getProduct(String id) {
        return inventory.get(id);
    }

    public List<Product> findByName(String name) {
        return inventory.values().stream().filter(
                        p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> findByCategory(String category) {
        return inventory.values().stream().filter(
                        p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> findByPriceRange(double lower, double upper) {
        return inventory.values().stream()
                .filter(p -> p.getPrice() >= lower && p.getPrice() <= upper)
                .collect(Collectors.toList());
    }

    public boolean updateQuantity(String id, int quantity) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setQuantity(quantity);
            return true;
        }
        return false;
    }

    public List<Product> getProductsWithSortedByPrice(boolean ascending) {
        return inventory.values().stream()
                .sorted(ascending ?
                        Comparator.comparingDouble(Product::getPrice) :
                        Comparator.comparingDouble(Product::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }

}
