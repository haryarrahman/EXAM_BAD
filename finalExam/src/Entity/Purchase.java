package Entity;

public class Purchase {
    private int Purchase_ID, Vendor_ID;
    String Purchase_Date, Vendor_RefNo;

    public Purchase() {

    }

    public Purchase(int purchase_ID, int vendor_ID, String purchase_Date, String vendor_RefNo) {
        Purchase_ID = purchase_ID;
        Vendor_ID = vendor_ID;
        Purchase_Date = purchase_Date;
        Vendor_RefNo = vendor_RefNo;
    }

    public int getPurchase_ID() {
        return Purchase_ID;
    }

    public void setPurchase_ID(int purchase_ID) {
        Purchase_ID = purchase_ID;
    }

    public int getVendor_ID() {
        return Vendor_ID;
    }

    public void setVendor_ID(int vendor_ID) {
        Vendor_ID = vendor_ID;
    }

    public String getPurchase_Date() {
        return Purchase_Date;
    }

    public void setPurchase_Date(String purchase_Date) {
        Purchase_Date = purchase_Date;
    }

    public String getVendor_RefNo() {
        return Vendor_RefNo;
    }

    public void setVendor_RefNo(String vendor_RefNo) {
        Vendor_RefNo = vendor_RefNo;
    }
}
