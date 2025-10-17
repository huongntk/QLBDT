package DAO;

import DTO.Product;

import UTIL.DBConnect;
import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {

    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> list = new ArrayList<>();
        try {
            Connection conn = DBConnect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SanPham");
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("MaLoai"),
                    rs.getInt("MaNCC"),
                    rs.getInt("SoLuong"),
                    rs.getString("HinhAnh"),
                    rs.getFloat("DonGia"),
                    rs.getString("MoTa"),
                    
                    rs.getBoolean("TrangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteProduct(int productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
