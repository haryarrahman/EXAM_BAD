package Dao;

import Entity.Purchase_Detail;
import Util.DaoService;
import Util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDetailDaoImpl implements DaoService<Purchase_Detail> {
    @Override
    public List<Purchase_Detail> fetchAll() throws SQLException, ClassNotFoundException {

        List<Purchase_Detail> purchase_details = new ArrayList<>();
        String query = "SELECT * FROM Purchase_Detail";

        try(Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Purchase_Detail purchase_detail = new Purchase_Detail();
                        purchase_detail.setPurchase_Detail_ID(rs.getInt("Purchase_Detail_ID"));
                        purchase_detail.setPurchase_ID(rs.getInt("Purschase_ID"));
                        purchase_detail.setProduct_ID(rs.getInt("Product_ID"));
                        purchase_detail.setQuantity(rs.getInt("Quantity"));
                        purchase_detail.setPrice(rs.getInt("Price"));
                        purchase_details.add(purchase_detail);
                    }
                }
            }
        }


        return purchase_details;
    }

    @Override
    public int addData(Purchase_Detail purchase_detail) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "INSERT INTO Purchase_Detail(Purchase_Detail_ID,Purchase_ID,Product_ID,Quantity,Price) VALUES(?,?,?,?,?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, purchase_detail.getPurchase_Detail_ID());
                ps.setInt(2, purchase_detail.getPurchase_ID());
                ps.setInt(3, purchase_detail.getProduct_ID());
                ps.setInt(4, purchase_detail.getQuantity());
                ps.setInt(5, purchase_detail.getPrice());
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
}
