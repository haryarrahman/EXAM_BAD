package View;

import Dao.ProductDaoImpl;
import Dao.PurchaseDaoImpl;
import Dao.PurchaseDetailDaoImpl;
import Dao.VendorDaoImpl;
import Entity.Product;
import Entity.Purchase;
import Entity.Purchase_Detail;
import Entity.Vendor;
import Util.CustomField;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PembelianForm {
    private JPanel rootPanel;
    private JButton viewButton;
    private JButton simpanButton;
    private JTextField fieldPurchaseID;
    private JTextField fieldPurchaseDate;
    private JTextField fieldPurchaseVendorID;
    private JTextField fieldPurchaseVendorRefNo;
    private JLabel fieldViewVendorName;
    private JTextField productID1;
    private JTextField productName1;
    private JTextField quantity1;
    private JTextField price1;
    private JTextField subTotal1;
    private JTextField subTotal2;
    private JTextField subTotal3;
    private JTextField price2;
    private JTextField price3;
    private JTextField quantity2;
    private JTextField quantity3;
    private JTextField productName2;
    private JTextField productName3;
    private JTextField productID2;
    private JTextField productID3;
    private JButton keluarButton;
    private JButton hitungButton;
    private JLabel fieldGrandTotal;
    private PurchaseDaoImpl purchaseDao;
    private List<Purchase> purchases;
    private VendorDaoImpl vendorDao;
    private Vendor vendor;
    private ProductDaoImpl productDao;
    private Product product;
    private PurchaseDetailDaoImpl purchaseDetailDao;

    CustomField.TextField fieldPurchaseIDDocument = new CustomField.TextField(10, "^[0-9]+$");
    CustomField.CustomDateTextField fieldDateDocument = new CustomField.CustomDateTextField(10);
    CustomField.TextField fieldVendorIDDocument = new CustomField.TextField(10, "^[0-9]+$");
    CustomField.TextField fieldPurchaseVendorRefNoDocument = new CustomField.TextField(24, "^[a-zA-Z0-9/]+$");
    public PembelianForm() {

        fieldPurchaseID.setDocument(fieldPurchaseIDDocument.getDocument());
        fieldPurchaseDate.setDocument(fieldDateDocument.getDocument());
        fieldPurchaseVendorID.setDocument(fieldVendorIDDocument.getDocument());
        fieldPurchaseVendorRefNo.setDocument(fieldPurchaseVendorRefNoDocument.getDocument());

        // Hit Purchase
        purchaseDao = new PurchaseDaoImpl();
        purchases = new ArrayList<>();
        try {
            purchases.addAll(purchaseDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        fieldPurchaseID.setText(String.valueOf(purchases.size() + 1));

        viewButton.addActionListener(e -> {
            if (!fieldPurchaseVendorID.getText().isEmpty()) {
                // Hit Vendor
                vendorDao = new VendorDaoImpl();
                try {
                    vendor = vendorDao.findById(Integer.parseInt(fieldPurchaseVendorID.getText()));
                    if (vendor != null) {
                        fieldViewVendorName.setText(vendor.getVendor_Name());
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, "Vendor belum terdaftar", "Warning", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException | ClassNotFoundException i) {
                    throw new RuntimeException(i);
                }

            } else {
                JOptionPane.showMessageDialog(rootPanel, "Masukkan Vendor ID", "Warning", JOptionPane.ERROR_MESSAGE);
            }


        });

        simpanButton.addActionListener(e -> {

            boolean isExist = false;

            if (!fieldPurchaseVendorID.getText().isEmpty()) {
                // Hit Vendor
                vendorDao = new VendorDaoImpl();
                try {
                    vendor = vendorDao.findById(Integer.parseInt(fieldPurchaseVendorID.getText()));
                    if (vendor != null) {
                        isExist = true;
                        fieldViewVendorName.setText(vendor.getVendor_Name());
                    }
                } catch (SQLException | ClassNotFoundException i) {
                    throw new RuntimeException(i);
                }

            } else {
                JOptionPane.showMessageDialog(rootPanel, "Masukkan Vendor ID", "Warning", JOptionPane.ERROR_MESSAGE);
            }

            if (!isExist) {
                JOptionPane.showMessageDialog(rootPanel, "Harap Masukkan Vendor ID yang Terdaftar", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {

                // Simpan Data Purchase, Purchase_Detail, update stock Product
                if (generateWarning() != "") {
                    JOptionPane.showMessageDialog(rootPanel, generateWarning(), "Warning", JOptionPane.ERROR_MESSAGE);
                } else {
                    Purchase purchase = new Purchase();
                    purchase.setPurchase_ID(Integer.parseInt(fieldPurchaseID.getText()));
                    purchase.setPurchase_Date(fieldPurchaseDate.getText());
                    purchase.setVendor_ID(Integer.parseInt(fieldPurchaseVendorID.getText()));
                    purchase.setVendor_RefNo(fieldPurchaseVendorRefNo.getText());

                    Purchase_Detail purchase_detail1 = new Purchase_Detail();
                    Purchase_Detail purchase_detail2 = new Purchase_Detail();
                    Purchase_Detail purchase_detail3 = new Purchase_Detail();

                    Product product1 = new Product();
                    Product product2 = new Product();
                    Product product3 = new Product();

                    if (!productID1.getText().trim().isEmpty()) {
                        purchase_detail1.setPurchase_Detail_ID(Integer.parseInt(productID1.getText()));
                        purchase_detail1.setPurchase_ID(Integer.parseInt(fieldPurchaseID.getText()));
                        purchase_detail1.setProduct_ID(Integer.parseInt(productID1.getText()));
                        purchase_detail1.setQuantity(Integer.parseInt(quantity1.getText()));
                        purchase_detail1.setPrice(Integer.parseInt(price1.getText()));

                        product1.setProduct_ID(Integer.parseInt(productID1.getText()));
                        product1.setProduct_Name(productName1.getText());
                        product1.setStock(Integer.parseInt(quantity1.getText()));
                    }

                    if (!productID2.getText().trim().isEmpty()) {
                        purchase_detail2.setPurchase_Detail_ID(Integer.parseInt(productID2.getText()));
                        purchase_detail2.setPurchase_ID(Integer.parseInt(fieldPurchaseID.getText()));
                        purchase_detail2.setProduct_ID(Integer.parseInt(productID2.getText()));
                        purchase_detail2.setQuantity(Integer.parseInt(quantity2.getText()));
                        purchase_detail2.setPrice(Integer.parseInt(price2.getText()));

                        product2.setProduct_ID(Integer.parseInt(productID2.getText()));
                        product2.setProduct_Name(productName2.getText());
                        product2.setStock(Integer.parseInt(quantity2.getText()));
                    }

                    if (!productID3.getText().trim().isEmpty()) {
                        purchase_detail3.setPurchase_Detail_ID(Integer.parseInt(productID3.getText()));
                        purchase_detail3.setPurchase_ID(Integer.parseInt(fieldPurchaseID.getText()));
                        purchase_detail3.setProduct_ID(Integer.parseInt(productID3.getText()));
                        purchase_detail3.setQuantity(Integer.parseInt(quantity3.getText()));
                        purchase_detail3.setPrice(Integer.parseInt(price3.getText()));

                        product3.setProduct_ID(Integer.parseInt(productID3.getText()));
                        product3.setProduct_Name(productName3.getText());
                        product3.setStock(Integer.parseInt(quantity3.getText()));
                    }

                    try {
                        if (
                                purchaseDao.addData(purchase) == 1
                        ) {
                            purchases.clear();
                            purchases.addAll(purchaseDao.fetchAll());

                            if (!productID1.getText().trim().isEmpty()) {
                                updatePurchaseDetail(purchase_detail1, product1);
                            }

                            if(!productID2.getText().trim().isEmpty()) {
                                updatePurchaseDetail(purchase_detail2, product2);
                            }

                            if(!productID3.getText().trim().isEmpty()) {
                                updatePurchaseDetail(purchase_detail3,product3);
                            }

                            clearAndReset();
                        }

                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                }

            }
        });

        keluarButton.addActionListener(e -> {
            System.exit(0);
        });

        hitungButton.addActionListener(e -> {
            if (generateWarning() != "") {
                JOptionPane.showMessageDialog(rootPanel, "Harap Masukkan semua field terlebih dahulu", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                int grandTotal = 0;

                if (!productID1.getText().trim().isEmpty()) {
                    int subTotal = Integer.parseInt(price1.getText()) * Integer.parseInt(quantity1.getText());
                    subTotal1.setText(String.valueOf(subTotal));
                    grandTotal = grandTotal + subTotal;
                }

                if (!productID2.getText().trim().isEmpty()) {
                    int subTotal = Integer.parseInt(price2.getText()) * Integer.parseInt(quantity2.getText());
                    subTotal2.setText(String.valueOf(subTotal));
                    grandTotal = grandTotal + subTotal;
                }

                if (!productID3.getText().trim().isEmpty()) {
                    int subTotal = Integer.parseInt(price3.getText()) * Integer.parseInt(quantity3.getText());
                    subTotal3.setText(String.valueOf(subTotal));
                    grandTotal = grandTotal + subTotal;
                }

                fieldGrandTotal.setText(String.valueOf(grandTotal));
            }
        });
    }

    public int getSubTotal(JTextField quantity, JTextField price){
        int newQuantity = Integer.parseInt(quantity.getText() != "" ? "0" : quantity.getText());
        int newPrice = Integer.parseInt(price.getText() != "" ? "0" : price.getText());
        int newSubTotal = newQuantity * newPrice;
        return newSubTotal;
    }

    public void updatePurchaseDetail(Purchase_Detail purchase_detail, Product product) {
        purchaseDetailDao = new PurchaseDetailDaoImpl();
        try {
            if (purchaseDetailDao.addData(purchase_detail) == 1) {
                productDao = new ProductDaoImpl();
                try {
                    Product currProduct = productDao.findById(purchase_detail.getProduct_ID());
                    if ( currProduct != null) { // Update Product
                        Product newProduct = new Product();
                        newProduct.setProduct_ID(product.getProduct_ID());
                        newProduct.setProduct_Name(product.getProduct_Name());
                        newProduct.setStock(currProduct.getStock() + product.getStock());
                        productDao.updateData(newProduct);
                    } else { // Add Product
                        productDao.addData(product);
                    }
                } catch (SQLException | ClassNotFoundException i) {
                    throw new RuntimeException(i);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearAndReset() {
        fieldPurchaseIDDocument.setText("");
        fieldDateDocument.setText("");
        fieldVendorIDDocument.setText("");
        fieldPurchaseVendorRefNoDocument.setText("");

        productID1.setText("");
        productName1.setText("");
        quantity1.setText("");
        price1.setText("");

        productID2.setText("");
        productName2.setText("");
        quantity2.setText("");
        price2.setText("");

        productID3.setText("");
        productName3.setText("");
        quantity3.setText("");
        price3.setText("");
    }
    public String generateWarning() {
        String warning = "";

        if (
                fieldPurchaseID.getText().trim().isEmpty() ||
                        fieldPurchaseDate.getText().trim().isEmpty() ||
                        fieldPurchaseVendorID.getText().trim().isEmpty() ||
                        fieldPurchaseVendorRefNo.getText().trim().isEmpty()
        ) {
           warning = "Harap masukan seluruh field Purchase";
        }

        if(
            productID1.getText().trim().isEmpty() &&
                    productID2.getText().trim().isEmpty() &&
                    productID3.getText().trim().isEmpty()
        ) {
            warning = "Harap masukan salah satu Product ID";
        } else {

            if (!productID1.getText().trim().isEmpty()) {
                if(productName1.getText().trim().isEmpty() ||
                        quantity1.getText().trim().isEmpty() ||
                        price1.getText().trim().isEmpty()
                ) {
                    warning = "Harap masukan seluruh field PURCHASE DETAIL pada Product ID 1";
                }
            } else if (!productID2.getText().trim().isEmpty()){
                if(productName2.getText().trim().isEmpty() ||
                        quantity2.getText().trim().isEmpty() ||
                        price2.getText().trim().isEmpty()
                ) {
                    warning = "Harap masukan seluruh field PURCHASE DETAIL pada Product ID 2";
                }
            } else if (!productID3.getText().trim().isEmpty()) {
                if(productName3.getText().trim().isEmpty() ||
                        quantity3.getText().trim().isEmpty() ||
                        price3.getText().trim().isEmpty()
                ) {
                    warning = "Harap masukan seluruh field PURCHASE DETAIL pada Product ID 3";
                }
            }

        }

        return warning;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pembelian");
        frame.setContentPane(new PembelianForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
