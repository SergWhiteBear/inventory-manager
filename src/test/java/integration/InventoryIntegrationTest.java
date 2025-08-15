package integration;

import model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import service.InventoryService;
import util.InventoryFileManager;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryIntegrationTest {

    private final String tempFileName = "integration_inventory.json";

    @AfterEach
    void cleanup() throws Exception {
        Files.deleteIfExists(new File(tempFileName).toPath());
    }

    @Test
    void fullInventoryFlow_ShouldWorkCorrectly() throws Exception {
        InventoryService service = new InventoryService();

        service.addProduct("Apple", "Fruits", 1.5, 10);
        service.addProduct("Orange", "Fruits", 2.0, 5);

        InventoryFileManager.saveInventory(
                service.getAllProducts().stream().collect(
                        java.util.stream.Collectors.toMap(Product::getId, p -> p)
                ),
                tempFileName
        );
        assertTrue(Files.exists(new File(tempFileName).toPath()));

        Map<String, Product> loaded = InventoryFileManager.loadInventory(tempFileName);
        assertEquals(2, loaded.size());
        assertTrue(loaded.containsKey("1"));
        assertEquals("Apple", loaded.get("1").getName());
        assertEquals(10, loaded.get("1").getQuantity());
    }
}
