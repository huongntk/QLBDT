package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Product;
import UTIL.DBConnect;

public class ProductDAO {

    public ArrayList<Product> getALLProduct() {
        ArrayList<Product> productList = new ArrayList<>();

        try (Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement("select * from SanPham;");
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("MaSP"));
                p.setProductName(rs.getString("TenSP"));
                p.setCategoryId(rs.getInt("MaLoai"));
                p.setSupplierId(rs.getInt("MaNCC"));
                p.setQuantity(rs.getInt("SoLuong"));
                p.setImgUrl(rs.getString("HinhAnh"));
                p.setPrice(rs.getInt("DonGia"));
                p.setDescription(rs.getString("MoTa"));
                p.setStatus(rs.getBoolean("TrangThai"));
                productList.add(p);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi đọc sản phẩm từ CSDL:");
            e.printStackTrace();
        }

        return productList;
    }

    public void addProduct(Product p) {
        String sql = "INSERT INTO SanPham (TenSP, MaLoai, MaNCC, SoLuong, HinhAnh, DonGia, MoTa, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setInt(3, p.getSupplierId());
            ps.setInt(4, p.getQuantity());
            ps.setString(5, p.getImgUrl());
            ps.setFloat(6, p.getPrice());
            ps.setString(7, p.getDescription());
            ps.setBoolean(8, p.getStatus());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thêm sản phẩm thành công!");
            } else {
                System.out.println("Thêm sản phẩm thất bại!");
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm sản phẩm vào CSDL:");
            e.printStackTrace();
        }
    }
}
