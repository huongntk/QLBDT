package DAO;

import DTO.NhanVienDTO;
import UTIL.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhanVienDAO {

  
    public ArrayList<NhanVienDTO> getAllNhanVien() {
        ArrayList<NhanVienDTO> dsNhanVien = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setHo(rs.getString("Ho"));
                nv.setTen(rs.getString("Ten"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                dsNhanVien.add(nv);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi đọc danh sách nhân viên:");
            e.printStackTrace();
        }
        return dsNhanVien;
    }
    
  
    public NhanVienDTO getNhanVienBySdt(String sdt) {
        String sql = "SELECT * FROM NhanVien WHERE SoDienThoai = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NhanVienDTO nv = new NhanVienDTO();
                    nv.setMaNV(rs.getInt("MaNV"));
                    nv.setHo(rs.getString("Ho"));
                    nv.setTen(rs.getString("Ten"));
                    nv.setGioiTinh(rs.getString("GioiTinh"));
                    nv.setSoDienThoai(rs.getString("SoDienThoai"));
                    nv.setChucVu(rs.getString("ChucVu"));
                    nv.setTrangThai(rs.getBoolean("TrangThai"));
                    return nv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

  
    public boolean addNhanVien(NhanVienDTO nv) {
        String sql = "INSERT INTO NhanVien (Ho, Ten, GioiTinh, SoDienThoai, ChucVu, TrangThai) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHo());
            ps.setString(2, nv.getTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getChucVu());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm nhân viên:");
            e.printStackTrace();
            return false;
        }
    }

  
    public boolean updateNhanVien(NhanVienDTO nv) {
        String sql = "UPDATE NhanVien SET Ho=?, Ten=?, GioiTinh=?, SoDienThoai=?, ChucVu=? WHERE MaNV=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHo());
            ps.setString(2, nv.getTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getChucVu());
            ps.setInt(6, nv.getMaNV());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật nhân viên:");
            e.printStackTrace();
            return false;
        }
    }

 
    public boolean deleteNhanVien(int maNV) {
        String sql = "UPDATE NhanVien SET TrangThai = 0 WHERE MaNV = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNV);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa nhân viên:");
            e.printStackTrace();
            return false;
        }
    }
}