package com.vector;


import com.vector.eshop.commands.Commands;
import com.vector.eshop.model.Category;
import com.vector.eshop.model.Product;
import com.vector.eshop.service.CategoryService;
import com.vector.eshop.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class EshopMain implements Commands {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CategoryService CATEGORY_SERVICE = new CategoryService();
    private static final ProductService PRODUCT_SERVICE = new ProductService();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printAllCommands();
            String command = SCANNER.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_CATEGORY:
                    addCategory();
                    break;
                case EDIT_CATEGORY_BY_ID:
                    updateCategory();
                    break;
                case DELETE_CATEGORY_BY_ID:
                    deleteCategory();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case EDIT_PRODUCT_BY_ID:
                    updateProduct();
                    break;
                case DELETE_PRODUCT_BY_ID:
                    deleteProduct();
                    break;
                case PRINT_SUM_OF_PRODUCTS:
                    System.out.println("Sum of products quantity -> " + PRODUCT_SERVICE.getSumOfProducts());
                    break;
                case PRINT_MAX_OF_PRICE_PRODUCT:
                    System.out.println("Maximum price -> " + PRODUCT_SERVICE.getMaxOfPriceProducts());
                    break;
                case PRINT_MIN_OF_PRICE_PRODUCT:
                    System.out.println("Minimum price -> " + PRODUCT_SERVICE.getMinOfPriceProducts());
                    break;
                case PRINT_AVG_OF_PRICE_PRODUCT:
                    System.out.println("Avg of price -> " + PRODUCT_SERVICE.getAVGPriceOfProducts());
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }


    private static void addCategory() {
        System.out.println("Enter the name of the category:");
        String categoryName = SCANNER.nextLine();
        List<Category> allCategories = CATEGORY_SERVICE.getAllCategories();
        if (!allCategories.isEmpty()) {
            for (Category allCategory : allCategories) {
                if (allCategory.getName().equals(categoryName)) {
                    System.out.println("Category already exists!");
                    return;
                } else {
                    Category category = new Category(categoryName);
                    CATEGORY_SERVICE.add(category);
                    System.out.println("Category added!");
                }
            }
        } else {
            Category category = new Category(categoryName);
            CATEGORY_SERVICE.add(category);
            System.out.println("Category added!");
        }

    }

    private static void updateCategory() {
        CATEGORY_SERVICE.getAllCategories().forEach(System.out::println);
        System.out.println("Enter ID of the category for update");
        try {
            int categoryId = Integer.parseInt(SCANNER.nextLine());
            Category category = CATEGORY_SERVICE.getCategoryById(categoryId);
            if (category != null) {
                System.out.println("Please enter the new name:");
                String categoryName = SCANNER.nextLine();
                category.setName(categoryName);
                CATEGORY_SERVICE.updateCategory(category);
                System.out.println("Category updated!");
            } else {
                System.out.println("Category not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!");
        }

    }

    private static void deleteCategory() {
        CATEGORY_SERVICE.getAllCategories().forEach(System.out::println);
        System.out.println("Enter ID of the category for DELETE");
        try {
            int categoryId = Integer.parseInt(SCANNER.nextLine());
            Category category = CATEGORY_SERVICE.getCategoryById(categoryId);
            if (category != null) {
                CATEGORY_SERVICE.deleteCategoryById(categoryId);
                System.out.println("Category DELETED!");
            } else {
                System.out.println("Category not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!");
        }
    }

    private static void addProduct() {
        CATEGORY_SERVICE.getAllCategories().forEach(System.out::println);
        System.out.println("Please enter ID of category for product");
        try {
            int categoryId = Integer.parseInt(SCANNER.nextLine());
            Category category = CATEGORY_SERVICE.getCategoryById(categoryId);
            if (category != null) {
                System.out.println("Please enter the name of the product:");
                String productName = SCANNER.nextLine();
                System.out.println("Please enter product description:");
                String productDescription = SCANNER.nextLine();
                System.out.println("Please enter product price:");
                double productPrice = Double.parseDouble(SCANNER.nextLine());
                System.out.println("Please enter product quantity:");
                int productQuantity = Integer.parseInt(SCANNER.nextLine());
                Product product = new Product(productName, productDescription, productPrice, productQuantity, category);
                PRODUCT_SERVICE.add(product);
                System.out.println("Product added!");
            } else {
                System.out.println("Category not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid keyword!");
        }
    }


    private static void updateProduct() {
        PRODUCT_SERVICE.getAllProducts().forEach(System.out::println);
        System.out.println("Enter ID of the product for update");
        try {
            int productId = Integer.parseInt(SCANNER.nextLine());
            Product productById = PRODUCT_SERVICE.getProductById(productId);
            if (productById != null) {
                CATEGORY_SERVICE.getAllCategories().forEach(System.out::println);
                System.out.println("Please enter ID of the category for update category");
                int categoryId = Integer.parseInt(SCANNER.nextLine());
                Category category = CATEGORY_SERVICE.getCategoryById(categoryId);
                if (category != null) {
                    System.out.println("Please enter the new name:");
                    String productName = SCANNER.nextLine();
                    System.out.println("Please enter product new description:");
                    String productDescription = SCANNER.nextLine();
                    System.out.println("Please enter product new price:");
                    double productPrice = Double.parseDouble(SCANNER.nextLine());
                    System.out.println("Please enter product new quantity:");
                    int productQuantity = Integer.parseInt(SCANNER.nextLine());
                    Product product = new Product(productId, productName, productDescription, productPrice, productQuantity, category);
                    PRODUCT_SERVICE.updateProduct(product);
                } else {
                    System.out.println("Category not found!");
                }

            } else {
                System.out.println("Product not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid keyword!");
        }
    }

    private static void deleteProduct() {
        PRODUCT_SERVICE.getAllProducts().forEach(System.out::println);
        System.out.println("Enter ID of the product for DELETE");
        try {
            int productId = Integer.parseInt(SCANNER.nextLine());
            Product productById = PRODUCT_SERVICE.getProductById(productId);
            if (productById != null) {
                PRODUCT_SERVICE.deleteProduct(productId);
                System.out.println("Product deleted!");
            } else {
                System.out.println("Product not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!");
        }
    }
}
