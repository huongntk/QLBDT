package DAO;

import DTO.KhuyenMai;
import UTIL.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class KhuyenMaiDAO {
    
    public ArrayList<KhuyenMai> layDanhSachKhuyenMai() {
        ArrayList<KhuyenMai> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM GiamGia";
        ResultSet rs = null;
        
        try {
          
            rs = DataProvider.executeQuery(sql);

            
            while (rs != null && rs.next()) {
                KhuyenMai km = new KhuyenMai(
                        rs.getInt("MaGG"),
                        rs.getString("TenGG"),
                        rs.getInt("PhanTramGiam"),
                        rs.getInt("DieuKien"),
                        rs.getDate("NgayBD"),
                        rs.getDate("NgayKT"),
                        rs.getBoolean("TinhTrang")
                );
                danhSach.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
         
            if (rs != null) {
                try {
                    Statement stmt = rs.getStatement();
                    Connection conn = stmt.getConnection();
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return danhSach;
    }
    
    public boolean themKhuyenMai(KhuyenMai km) {
        String sql = "INSERT INTO GiamGia (TenGG, PhanTramGiam, DieuKien, NgayBD, NgayKT) VALUES (?, ?, ?, ?, ?)";
        
        int rows = DataProvider.executeUpdate(sql,
                km.getTenGG(),
                km.getPhanTramGiam(),
                km.getDieuKien(),
                km.getNgayBD(),
                km.getNgayKT()
        );
        
        return rows > 0;
    }

    public boolean suaKhuyenMai(KhuyenMai km) {
        String sql = "UPDATE GiamGia SET TenGG = ?, PhanTramGiam = ?, DieuKien = ?, NgayBD = ?, NgayKT = ?, TinhTrang = ? WHERE MaGG = ?";
        
        int rows = DataProvider.executeUpdate(sql,
                km.getTenGG(),
                km.getPhanTramGiam(),
                km.getDieuKien(),
                km.getNgayBD(),
                km.getNgayKT(),
                km.isTinhTrang(),
                km.getMaGG() 
        );
        
        return rows > 0;
    }
    
    public boolean xoaKhuyenMai(int maGG) {
        String sql = "UPDATE GiamGia SET TinhTrang = 0 WHERE MaGG = ?";
        
        int rows = DataProvider.executeUpdate(sql, maGG);
        
        return rows > 0;
    }
    public ArrayList<KhuyenMai> timKiemKhuyenMai(String tuKhoa) {
        ArrayList<KhuyenMai> danhSach = new ArrayList<>();
    
        // SQL chuẩn: CAST các cột số sang VARCHAR để LIKE
        String sql = "SELECT * FROM GiamGia " +
                     "WHERE CAST(MaGG AS VARCHAR) LIKE ? " +
                     "OR TenGG LIKE ? " +
                     "OR CAST(DieuKien AS VARCHAR) LIKE ?";
    
        String keyword = "%" + tuKhoa + "%";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            // Truyền tham số
            stmt.setString(1, keyword);
            stmt.setString(2, keyword);
            stmt.setString(3, keyword);

            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getInt("MaGG"),
                    rs.getString("TenGG"),
                    rs.getInt("PhanTramGiam"),
                    rs.getInt("DieuKien"),
                    rs.getDate("NgayBD"),
                    rs.getDate("NgayKT"),
                    rs.getBoolean("TinhTrang")
                );
                danhSach.add(km);
            }   

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }
}
