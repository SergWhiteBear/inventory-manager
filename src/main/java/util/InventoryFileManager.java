package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class InventoryFileManager {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveInventory(Map<String, Product> inventory, String fileName) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), inventory);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Map<String, Product> loadInventory(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return new HashMap<>(); // Если файла нет — пустой склад
            }
            return objectMapper.readValue(file, new TypeReference<Map<String, Product>>() {
            });
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке из файла: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
