package Dao;

import Entity.Vendor;
import Util.DaoServiceVendor;
import Util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendorDaoImpl implements DaoServiceVendor<Vendor> {

    @Override
    public List<Vendor> fetchAll() throws SQLException, ClassNotFoundException {

        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT * FROM Vendor";

        try(Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Vendor vendor = new Vendor();
                        vendor.setVendor_ID(rs.getInt("Vendor_ID"));
                        vendor.setVendor_Name(rs.getString("Vendor_Name"));
                        vendor.setVendor_Add(rs.getString("Vendor_Add"));
                        vendors.add(vendor);
                    }
                }
            }
        }


        return vendors;
    }

    @Override
    public int addData(Vendor vendor) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "INSERT INTO Vendor(Vendor_ID,Vendor_Name,Vendor_Add) VALUES(?,?,?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, vendor.getVendor_ID());
                ps.setString(2, vendor.getVendor_Name());
                ps.setString(3, vendor.getVendor_Add());
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
    public Vendor findById(int vendorID) throws SQLException, ClassNotFoundException {
        Vendor vendor = null;
        String query = "SELECT * FROM Vendor WHERE Vendor_ID = ?";

        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, vendorID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        vendor = new Vendor();
                        vendor.setVendor_ID(rs.getInt("Vendor_ID"));
                        vendor.setVendor_Name(rs.getString("Vendor_Name"));
                        vendor.setVendor_Add(rs.getString("Vendor_Add"));
                    }
                }
            }
        }

        return vendor;
    }
}
