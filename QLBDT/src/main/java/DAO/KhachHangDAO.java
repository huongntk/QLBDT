package DAO;

import DTO.KhachHangDTO;
import UTIL.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHangDAO {

   
    public ArrayList<KhachHangDTO> getAllKhachHang() {
        ArrayList<KhachHangDTO> dsKhachHang = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setHo(rs.getString("Ho"));
                kh.setTen(rs.getString("Ten"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setTongChiTieu(rs.getInt("TongChiTieu"));
                kh.setTinhTrang(rs.getBoolean("TinhTrang"));
                dsKhachHang.add(kh);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi đọc danh sách khách hàng:");
            e.printStackTrace();
        }
        return dsKhachHang;
    }

   
    public KhachHangDTO getKhachHangBySdt(String sdt) {
        String sql = "SELECT * FROM KhachHang WHERE SoDienThoai = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    KhachHangDTO kh = new KhachHangDTO();
                    kh.setMaKH(rs.getInt("MaKH"));
                    kh.setHo(rs.getString("Ho"));
                    kh.setTen(rs.getString("Ten"));
                    kh.setGioiTinh(rs.getString("GioiTinh"));
                    kh.setSoDienThoai(rs.getString("SoDienThoai"));
                    kh.setTongChiTieu(rs.getInt("TongChiTieu"));
                    kh.setTinhTrang(rs.getBoolean("TinhTrang"));
                    return kh;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

   
    public boolean addKhachHang(KhachHangDTO kh) {
        String sql = "INSERT INTO KhachHang (Ho, Ten, GioiTinh, SoDienThoai, TinhTrang) VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHo());
            ps.setString(2, kh.getTen());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getSoDienThoai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm khách hàng:");
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean updateKhachHang(KhachHangDTO kh) {
        String sql = "UPDATE KhachHang SET Ho=?, Ten=?, GioiTinh=?, SoDienThoai=? WHERE MaKH=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHo());
            ps.setString(2, kh.getTen());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getSoDienThoai());
            ps.setInt(5, kh.getMaKH());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật khách hàng:");
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean deleteKhachHang(int maKH) {
        String sql = "UPDATE KhachHang SET TinhTrang = 0 WHERE MaKH = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKH);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa khách hàng:");
            e.printStackTrace();
            return false;
        }
    }
}