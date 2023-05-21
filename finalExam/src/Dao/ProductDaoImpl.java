package Dao;

import Entity.Product;
import Util.DaoServiceProduct;
import Util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements DaoServiceProduct<Product> {
    @Override
    public List<Product> fetchAll() throws SQLException, ClassNotFoundException {

        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";

        try(Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Product product = new Product();
                        product.setProduct_ID(rs.getInt("Product_ID"));
                        product.setProduct_Name(rs.getString("Product_Name"));
                        product.setStock(rs.getInt("Stock"));
                        products.add(product);
                    }
                }
            }
        }


        return products;
    }

    @Override
    public int addData(Product product) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "INSERT INTO Product(Product_ID,Product_Name,Stock) VALUES(?,?,?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, product.getProduct_ID());
                ps.setString(2, product.getProduct_Name());
                ps.setInt(3, product.getStock());
                if (ps.executeUpdate() != 0) {
                    connection.commit();
                    result = 1;
                } else{
                    connection.rollback();
                }
            }
        }

        return result;
    }

    @Override
    public Product findById(int productID) throws SQLException, ClassNotFoundException {
        Product product = null;
        String query = "SELECT * FROM Product WHERE Product_ID = ?";

        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, productID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        product = new Product();
                        product.setProduct_ID(rs.getInt("Product_ID"));
                        product.setProduct_Name(rs.getString("Product_Name"));
                        product.setStock(rs.getInt("Stock"));
                    }
                }
            }
        }

        return product;
    }

    @Override
    public int updateData(Product product) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "UPDATE Product SET Product_ID = ?, Product_Name = ?, Stock = ?";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, product.getProduct_ID());
                ps.setString(2, product.getProduct_Name());
                ps.setInt(3, product.getStock());
                if (ps.executeUpdate() != 0) {
                    connection.commit();
                    result = 1;
                } else {
                    connection.rollback();
                }
            }
        }

        return result;
    }
}
