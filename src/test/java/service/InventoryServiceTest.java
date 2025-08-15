package service;

import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService service;

    @BeforeEach
    void setUp() {
        service = new InventoryService();
    }

    @Test
    void addProduct_ShouldIncreaseInventorySize() {
        service.addProduct("Apple", "Fruits", 1.5, 10);
        assertEquals(1, service.getAllProducts().size());
    }

    @Test
    void removeProduct_ShouldRemoveIfExists() {
        service.addProduct("Apple", "Fruits", 1.5, 10);
        Product apple = service.findByName("Apple").get(0);
        assertTrue(service.removeProduct(apple));
        assertEquals(0, service.getAllProducts().size());
    }

    @Test
    void findByName_ShouldReturnCorrectProduct() {
        service.addProduct("Apple", "Fruits", 1.5, 10);
        assertFalse(service.findByName("Apple").isEmpty());
        assertTrue(service.findByName("Orange").isEmpty());
    }

    @Test
    void addProduct_ShouldThrowForNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addProduct("Apple", "Fruits", -1, 10));
    }
}
