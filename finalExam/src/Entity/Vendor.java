package Entity;

public class Vendor {

    private int Vendor_ID;
    private String Vendor_Name, Vendor_Add;

    public Vendor() {

    }

    public Vendor(int vendor_ID, String vendor_Name, String vendor_Add) {
        Vendor_ID = vendor_ID;
        Vendor_Name = vendor_Name;
        Vendor_Add = vendor_Add;
    }

    public int getVendor_ID() {
        return Vendor_ID;
    }

    public void setVendor_ID(int vendor_ID) {
        Vendor_ID = vendor_ID;
    }

    public String getVendor_Name() {
        return Vendor_Name;
    }

    public void setVendor_Name(String vendor_Name) {
        Vendor_Name = vendor_Name;
    }

    public String getVendor_Add() {
        return Vendor_Add;
    }

    public void setVendor_Add(String vendor_Add) {
        Vendor_Add = vendor_Add;
    }
}
