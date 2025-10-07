/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ltd96
 *         ProductDetail entity class
 */
public class ProductDetail {
    private int productDetailId;
    private int productId; // forein key to Product
    private String imei;
    private String color;
    private String storage;
    private String ram;
    private String battery;
    private String os;
    private int warranty; // month
    private int status; // 0 = unavailable, 1 = available ...

    // Constructor
    public ProductDetail() {
    }

    public ProductDetail(int productDetailId, int productId, String imei, String color,
            String storage, String ram, String battery, String os,
            int warranty, int status) {
        this.productDetailId = productDetailId;
        this.productId = productId;
        this.imei = imei;
        this.color = color;
        this.storage = storage;
        this.ram = ram;
        this.battery = battery;
        this.os = os;
        this.warranty = warranty;
        this.status = status;
    }

    // GETTER
    public int getProductDetailId() {
        return productDetailId;
    }

    public int getProductId() {
        return productId;
    }

    public String getImei() {
        return imei;
    }

    public String getColor() {
        return color;
    }

    public String getStorage() {
        return storage;
    }

    public String getRam() {
        return ram;
    }

    public String getBattery() {
        return battery;
    }

    public String getOs() {
        return os;
    }

    public int getWarranty() {
        return warranty;
    }

    public int getStatus() {
        return status;
    }

    // SETTER
    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductDetail{" + "productDetailId=" + productDetailId + ", productId=" + productId +
                ", imei=" + imei + ", color=" + color + ", storage=" + storage +
                ", ram=" + ram + ", battery=" + battery + ", os=" + os +
                ", warranty=" + warranty + ", status=" + status + '}';
    }
}
