package model;

public class Product {
    private int productId;
    private String productName;
    private String category;
    private double price;
    private int quantity;
    private int reorderLevel;

    public Product(int productId, String productName, String category, double price, int quantity, int reorderLevel) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getReorderLevel() { return reorderLevel; }
}