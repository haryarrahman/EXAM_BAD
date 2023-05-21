package Entity;

public class Purchase_Detail {
    private int Purchase_Detail_ID, Purchase_ID, Product_ID;
    private int Quantity, Price;

    public Purchase_Detail() {

    }

    public Purchase_Detail(int purchase_Detail_ID, int purchase_ID, int product_ID, int quantity, int price) {
        Purchase_Detail_ID = purchase_Detail_ID;
        Purchase_ID = purchase_ID;
        Product_ID = product_ID;
        Quantity = quantity;
        Price = price;
    }

    public int getPurchase_Detail_ID() {
        return Purchase_Detail_ID;
    }

    public void setPurchase_Detail_ID(int purchase_Detail_ID) {
        Purchase_Detail_ID = purchase_Detail_ID;
    }

    public int getPurchase_ID() {
        return Purchase_ID;
    }

    public void setPurchase_ID(int purchase_ID) {
        Purchase_ID = purchase_ID;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
