package Dao;

import Entity.Purchase;
import Util.DaoService;
import Util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDaoImpl implements DaoService<Purchase> {
    @Override
    public List<Purchase> fetchAll() throws SQLException, ClassNotFoundException {

        List<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM Purchase";

        try(Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Purchase purchase = new Purchase();
                        purchase.setPurchase_ID(rs.getInt("Purchase_ID"));
                        purchase.setVendor_ID(rs.getInt("Vendor_ID"));
                        purchase.setPurchase_Date(rs.getString("Purchase_Date"));
                        purchase.setVendor_RefNo(rs.getString("Vendor_RefNo"));
                        purchases.add(purchase);
                    }
                }
            }
        }


        return purchases;
    }

    @Override
    public int addData(Purchase purchase) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "INSERT INTO Purchase(Purchase_ID,Vendor_ID,Purchase_Date,Vendor_RefNo) VALUES(?,?,?,?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, purchase.getPurchase_ID());
                ps.setInt(2, purchase.getVendor_ID());
                ps.setString(3, purchase.getPurchase_Date());
                ps.setString(4, purchase.getVendor_RefNo());
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
