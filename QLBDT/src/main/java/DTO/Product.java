/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ltd9605
 *         Product entity class
 */
public class Product {
    private int productId;
    private String productName;
    private int categoryId; // foreign key to category
    private int supplierId; // foreign key to supplier
    private int quantity;
    private String imgUrl;
    private float price;
    private String description;
    private Boolean status;

    // Constructor
    public Product() {
    }

    public Product(int productId, String productName, int categoryId, int supplierId,
            int quantity, String imgUrl, float price, String description, Boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.imgUrl = imgUrl;
        this.price = price;
        this.description = description;
        this.status = status;
    }

    public Product(int id, String name, int quan, String url, float price, Boolean status) {
        this.productId = id;
        this.productName = name;
        this.quantity = quan;
        this.imgUrl = url;
        this.price = price;
        this.status = status;
    }

    // GETTER
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getStatus() {
        return status;
    }

    // SETTER
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String newName) {
        this.productName = newName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setQuantity(int newQuan) {
        this.quantity = newQuan;
    }

    public void setImgUrl(String newUrl) {
        this.imgUrl = newUrl;
    }

    public void setPrice(float newPrice) {
        this.price = newPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Boolean newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName +
                ", categoryId=" + categoryId + ", supplierId=" + supplierId +
                ", quantity=" + quantity + ", imgUrl=" + imgUrl +
                ", price=" + price + ", description=" + description +
                ", status=" + status + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return productId == other.productId;
    }
}
