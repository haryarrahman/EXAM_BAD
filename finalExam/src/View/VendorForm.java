package View;

import Dao.VendorDaoImpl;
import Entity.Vendor;
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

public class VendorForm {
    private JSplitPane rootPanel;
    private JTextField fieldVendorId;
    private JTextField fieldVendorName;
    private JTextField fieldVendorAdd;
    private JButton simpanButton;
    private JButton keluarButton;
    private JTable tableVendor;
    private JScrollPane tableScrollPane;
    private VendorTableModel vendorTableModel;
    private VendorDaoImpl vendorDao;
    private List<Vendor> vendors;


    CustomField.TextField fieldIdDocument = new CustomField.TextField(10, "^[0-9]+$");
    CustomField.TextField fieldNameDocument = new CustomField.TextField(149, "^[a-zA-Z0-9 ]+$");
    CustomField.TextField fieldAddDocument = new CustomField.TextField(99, "^[a-zA-Z0-9 ]+$");

    public VendorForm() {

        fieldVendorId.setDocument(fieldIdDocument.getDocument());
        fieldVendorName.setDocument(fieldNameDocument.getDocument());
        fieldVendorAdd.setDocument(fieldAddDocument.getDocument());

        fieldVendorId.setColumns(10);
        fieldVendorName.setColumns(15);
        fieldVendorAdd.setColumns(30);

        vendorDao = new VendorDaoImpl();
        vendors = new ArrayList<>();
        try {
            vendors.addAll(vendorDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Table Model
        vendorTableModel = new VendorTableModel(vendors);
        tableVendor.setModel(vendorTableModel);
        tableVendor.setAutoCreateRowSorter(true);

        // UI : Column Width
        TableColumnModel columnModel = tableVendor.getColumnModel();
        TableColumn column1 = columnModel.getColumn(0);
        column1.setPreferredWidth(100);
        TableColumn column2 = columnModel.getColumn(1);
        column2.setPreferredWidth(150);
        TableColumn column3 = columnModel.getColumn(2);
        column3.setPreferredWidth(250);

        // UI : Header & Body Table
        HeaderRenderer headerRenderer = new HeaderRenderer();
        tableVendor.getTableHeader().setDefaultRenderer(headerRenderer);

        BodyRenderer bodyRenderer = new BodyRenderer();
        tableVendor.setDefaultRenderer(Object.class, bodyRenderer);

        // UI : Table Grid Color
        tableVendor.setGridColor(Color.black);

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

            for (Vendor vendor : vendors) {
                if (vendor.getVendor_ID() == Integer.parseInt(fieldVendorId.getText())) {
                    isExists = true;
                }
            }


            if (fieldVendorId.getText().trim().isEmpty() || fieldVendorName.getText().trim().isEmpty() || fieldVendorAdd.getText().trim().isEmpty() ) {
                JOptionPane.showMessageDialog(rootPanel, "Harap Masukkan Seluruh Input", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if (isExists) {
                JOptionPane.showMessageDialog(rootPanel, "Harap Masukan ID yang belum terdaftar", "Gagal Registrasi", JOptionPane.ERROR_MESSAGE);
            } else {
                Vendor vendor = new Vendor();
                vendor.setVendor_ID(Integer.parseInt(fieldVendorId.getText()));
                vendor.setVendor_Name(fieldVendorName.getText());
                vendor.setVendor_Add(fieldVendorAdd.getText());

                try {
                    if (vendorDao.addData(vendor) == 1 ) {
                        vendors.clear();
                        vendors.addAll(vendorDao.fetchAll());
                        vendorTableModel.fireTableDataChanged();
                        clearAndReset();
                    }

                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vendor");
        frame.setContentPane(new VendorForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void clearAndReset(){
        fieldIdDocument.setText("");
        fieldNameDocument.setText("");
        fieldAddDocument.setText("");
    }


    private static class VendorTableModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Vendor ID", "Nama Vendor", "Alamat Vendor"};
        private java.util.List<Vendor> vendors;

        private VendorTableModel(List<Vendor> vendors) {
            this.vendors = vendors ;
        }

        @Override
        public int getRowCount() {
            return vendors.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (vendors.size() > 0) {
                return switch (columnIndex) {
                    case 0 -> vendors.get(rowIndex).getVendor_ID();
                    case 1 -> vendors.get(rowIndex).getVendor_Name();
                    case 2 -> vendors.get(rowIndex).getVendor_Add();
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
