package Entity;

public class Product {
    private int Product_ID, Stock;
    private String Product_Name;

    public Product() {

    }

    public Product(int product_ID, int stock, String product_Name) {
        Product_ID = product_ID;
        Stock = stock;
        Product_Name = product_Name;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }
}
