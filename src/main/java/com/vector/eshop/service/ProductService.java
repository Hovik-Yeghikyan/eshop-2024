package com.vector.eshop.service;

import com.vector.eshop.db.DBConnectionProvider;
import com.vector.eshop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CategoryService categoryService = new CategoryService();

    public void add(Product product) {
        String sql = "INSERT INTO product(name,description,price,quantity,category_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getCategory().getId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build();
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getCategory().getId());
            ps.setInt(6, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSumOfProducts() {
        String sql = "SELECT SUM(quantity) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int sum = resultSet.getInt(1);
                return sum;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getMaxOfPriceProducts() {
        String sql = "SELECT MAX(price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double maxPrice = resultSet.getInt(1);
                return maxPrice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getMinOfPriceProducts() {
        String sql = "SELECT MIN(price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double minPrice = resultSet.getInt(1);
                return minPrice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getAVGPriceOfProducts() {
        String sql = "SELECT AVG(price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double avg = resultSet.getInt(1);
                return avg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

