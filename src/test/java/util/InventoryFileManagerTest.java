package util;

import model.Product;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFileManagerTest {

    @Test
    void saveAndLoadInventory_ShouldWorkCorrectly() throws Exception {
        String tempFileName = "test_inventory.json";

        Map<String, Product> products = Map.of(
                "1", new Product("Apple", "Fruits", 1.5, 10),
                "2", new Product("Orange", "Fruits", 2.0, 5)
        );

        InventoryFileManager.saveInventory(products, tempFileName);
        assertTrue(Files.exists(new File(tempFileName).toPath()));

        Map<String, Product> loaded = InventoryFileManager.loadInventory(tempFileName);
        assertEquals(2, loaded.size());
        assertEquals("Apple", loaded.get("1").getName());

        Files.deleteIfExists(new File(tempFileName).toPath());
    }
}
