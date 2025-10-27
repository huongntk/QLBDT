package DAO;

import DTO.KhachHangDTO;
import UTIL.DBConnect; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHangDAO {

    public ArrayList<KhachHangDTO> layDanhSachKhachHang() {
        ArrayList<KhachHangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                        rs.getInt("MaKH"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getInt("TongChiTieu"),
                        rs.getBoolean("TinhTrang")
                );
                danhSach.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean themKhachHang(KhachHangDTO kh) {
        String sql = "INSERT INTO KhachHang (Ho, Ten, GioiTinh, SoDienThoai, TinhTrang) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, kh.getHo());
            ps.setString(2, kh.getTen());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getSoDienThoai());
            ps.setBoolean(5, kh.isTinhTrang());

            return ps.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaKhachHang(KhachHangDTO kh) {
        String sql = "UPDATE KhachHang SET Ho = ?, Ten = ?, GioiTinh = ?, SoDienThoai = ?, TinhTrang = ? WHERE MaKH = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, kh.getHo());
            ps.setString(2, kh.getTen());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getSoDienThoai());
            ps.setBoolean(5, kh.isTinhTrang());
            ps.setInt(6, kh.getMaKH());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhachHang(int maKH) {
        String sql = "UPDATE KhachHang SET TinhTrang = 0 WHERE MaKH = ?"; 
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maKH);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<KhachHangDTO> timKiemKhachHang(String tuKhoa) {
        ArrayList<KhachHangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE CAST(MaKH AS VARCHAR) LIKE ? OR CONCAT(Ho, ' ', Ten) LIKE ? OR SoDienThoai LIKE ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhachHangDTO kh = new KhachHangDTO(
                            rs.getInt("MaKH"),
                            rs.getString("Ho"),
                            rs.getString("Ten"),
                            rs.getString("GioiTinh"),
                            rs.getString("SoDienThoai"),
                            rs.getInt("TongChiTieu"),
                            rs.getBoolean("TinhTrang")
                    );
                    danhSach.add(kh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}