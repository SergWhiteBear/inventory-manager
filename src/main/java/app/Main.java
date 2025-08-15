package app;

import model.Product;
import service.InventoryService;
import util.InventoryFileManager;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String fileName = "inventory.json";
        InventoryService service;
        Scanner sc = new Scanner(System.in);


        try {
            Map<String, Product> loadedProducts = InventoryFileManager.loadInventory(fileName);
            service = new InventoryService(loadedProducts);
            LOGGER.info(String.format("Загружено %d товаров", loadedProducts.size()));
        } catch (Exception ex) {
            LOGGER.warning(String.format("Ошибка при загрузке %s : %s", fileName, ex.getMessage()));
            service = new InventoryService();
        }
        while (true) {
            System.out.println("\n=== Inventory Manager ===");
            System.out.println("1. Добавить товар");
            System.out.println("2. Удалить товар");
            System.out.println("3. Найти товар");
            System.out.println("4. Показать все товары");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> addProduct(sc, service, fileName);
                case "2" -> removeProduct(sc, service, fileName);
                case "3" -> findProduct(sc, service);
                case "4" -> service.getAllProducts().forEach(System.out::println);
                case "0" -> {
                    LOGGER.info("Завершение работы программы");
                    saveToFile(service, fileName);
                    System.out.println("Склад успешно сохранён в " + fileName);
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }


    }

    private static void addProduct(Scanner scanner, InventoryService service, String fileName) {
        try {
            System.out.print("Введите название: ");
            String name = scanner.nextLine();

            System.out.print("Введите категорию: ");
            String category = scanner.nextLine();

            System.out.print("Введите цену: ");
            double price = Double.parseDouble(scanner.nextLine());
            if (price < 0) throw new IllegalArgumentException("Цена не может быть отрицательной");


            System.out.print("Введите количество: ");
            int qty = Integer.parseInt(scanner.nextLine());
            if (qty < 0) throw new IllegalArgumentException("Количество не может быть отрицательным");

            service.addProduct(name, category, price, qty);
            saveToFile(service, fileName);
            LOGGER.info("Товар успешно добавлен и сохранен");

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите число в поле цены и количества");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void removeProduct(Scanner scanner, InventoryService service, String fileName) {
        System.out.print("Введите название товара для удаления: ");
        String name = scanner.nextLine();
        service.findByName(name).stream().findFirst().ifPresentOrElse(
                product -> {
                    if (service.removeProduct(product)) {
                        saveToFile(service, fileName);
                        LOGGER.info(String.format("Товар '%s' удалён и изменения сохранены", product.getName()));
                    } else {
                        System.out.println("Не удалось удалить товар");
                    }
                },
                () -> System.out.println("Товар не найден!")
        );
    }

    private static void findProduct(Scanner scanner, InventoryService service) {
        System.out.print("Введите название продукта: ");
        String name = scanner.nextLine();
        service.findByName(name).stream().findFirst().ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Товар не найден!")
        );

    }

    private static void saveToFile(InventoryService service, String fileName) {
        try {
            InventoryFileManager.saveInventory(
                    service.getAllProducts().stream()
                            .collect(Collectors.toMap(Product::getId, p -> p)),
                    fileName
            );
        } catch (Exception e) {
            LOGGER.warning("Ошибка при сохранении файла: " + e.getMessage());
        }
    }
}
