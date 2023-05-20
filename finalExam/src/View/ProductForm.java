package View;

import Dao.ProductDaoImpl;
import Entity.Product;
import Util.CustomField;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductForm {
    private JSplitPane rootPanel;
    private JTextField fieldProductId;
    private JTextField fieldProductName;
    private JTextField fieldProductStock;
    private JButton simpanButton;
    private JButton keluarButton;
    private JTable tableProduct;
    private JScrollPane tableScrollPane;
    private ProductTableModel productTableModel;
    private ProductDaoImpl productDao;
    private List<Product> products;


    CustomField.TextField fieldIdDocument = new CustomField.TextField(10, "^[0-9]+$");
    CustomField.TextField fieldNameDocument = new CustomField.TextField(49, "^[a-zA-Z0-9 ]+$");
    CustomField.TextField fieldStockDocument = new CustomField.TextField(10, "^[0-9]+$");

    public ProductForm () {

        fieldProductId.setDocument(fieldIdDocument.getDocument());
        fieldProductName.setDocument(fieldNameDocument.getDocument());
        fieldProductStock.setDocument(fieldStockDocument.getDocument());

        fieldProductId.setColumns(10);
        fieldProductName.setColumns(15);
        fieldProductStock.setColumns(30);

        productDao = new ProductDaoImpl();
        products = new ArrayList<>();
        try {
            products.addAll(productDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Table Model
        productTableModel = new ProductTableModel(products);
        tableProduct.setModel(productTableModel);
        tableProduct.setAutoCreateRowSorter(true);

        // UI : Column Width
        TableColumnModel columnModel = tableProduct.getColumnModel();
        TableColumn column1 = columnModel.getColumn(0);
        column1.setPreferredWidth(100);
        TableColumn column2 = columnModel.getColumn(1);
        column2.setPreferredWidth(150);
        TableColumn column3 = columnModel.getColumn(2);
        column3.setPreferredWidth(250);

        // UI : Header & Body Table
        HeaderRenderer headerRenderer = new HeaderRenderer();
        tableProduct.getTableHeader().setDefaultRenderer(headerRenderer);

        BodyRenderer bodyRenderer = new BodyRenderer();
        tableProduct.setDefaultRenderer(Object.class, bodyRenderer);

        // UI : Table Grid Color
        tableProduct.setGridColor(Color.black);

        // UI : JScrollPane Color
        JViewport viewport = tableScrollPane.getViewport();
        viewport.setBackground(Color.decode("#ffe896"));

        // Action : Button Keluar
        keluarButton.addActionListener(e -> {
            System.exit(0);
        });

        // Action : Button Simpan
        simpanButton.addActionListener(e -> {

            boolean isExists = false;

            for (Product product : products) {
                if (product.getProduct_ID() == Integer.parseInt(fieldProductId.getText())) {
                    isExists = true;
                }
            }


            if (fieldProductId.getText().trim().isEmpty() || fieldProductName.getText().trim().isEmpty() || fieldProductStock.getText().trim().isEmpty() ) {
                JOptionPane.showMessageDialog(rootPanel, "Harap Masukkan Seluruh Input", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if (isExists) {
                JOptionPane.showMessageDialog(rootPanel, "Harap Masukan ID yang belum terdaftar", "Gagal Registrasi", JOptionPane.ERROR_MESSAGE);
            } else {
                Product product = new Product();
                product.setProduct_ID(Integer.parseInt(fieldProductId.getText()));
                product.setProduct_Name(fieldProductName.getText());
                product.setStock(Integer.parseInt(fieldProductStock.getText()));

                try {
                    if (productDao.addData(product) == 1 ) {
                        products.clear();
                        products.addAll(productDao.fetchAll());
                        productTableModel.fireTableDataChanged();
                        clearAndReset();
                    }

                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product");
        frame.setContentPane(new ProductForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void clearAndReset(){
        fieldIdDocument.setText("");
        fieldNameDocument.setText("");
        fieldStockDocument.setText("");
    }


    private static class ProductTableModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Product ID", "Nama Product", "Stock"};
        private java.util.List<Product> products;

        private ProductTableModel(List<Product> products) {
            this.products = products ;
        }

        @Override
        public int getRowCount() {
            return products.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (products.size() > 0) {
                return switch (columnIndex) {
                    case 0 -> products.get(rowIndex).getProduct_ID();
                    case 1 -> products.get(rowIndex).getProduct_Name();
                    case 2 -> products.get(rowIndex).getStock();
                    default -> "-";
                };
            } else {
                return  null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }else {
                return Object.class;
            }
        }


    }

    public class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer() {
            setOpaque(true);

            setForeground(Color.decode("#94b771"));
            setBackground(Color.decode("#395526"));

            setFont(new Font("Arial", Font.BOLD, 14));
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public class BodyRenderer extends DefaultTableCellRenderer {
        public BodyRenderer() {
            setHorizontalAlignment(CENTER);
            setFont(new Font("Arial", Font.PLAIN, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
