package DAO;

import UTIL.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date; 

public class ThongKeDAO {

    public ArrayList<Object[]> thongKeDoanhThu(Date ngayBD, Date ngayKT) {
        ArrayList<Object[]> danhSach = new ArrayList<>();
        String sql = "SELECT "
                   + "  CONVERT(varchar, NgayLap, 103) AS ThoiGian, " 
                   + "  COUNT(MaHD) AS SoHoaDon, "
                   + "  SUM(TongTien) AS TongTien "
                   + "FROM "
                   + "  HoaDon "
                   + "WHERE "
                   + "  NgayLap >= ? AND NgayLap <= ? "
                   + "GROUP BY "
                   + "  NgayLap "
                   + "ORDER BY "
                   + "  NgayLap ASC";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, ngayBD);
            ps.setDate(2, ngayKT);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[]{
                        rs.getString("ThoiGian"),
                        rs.getInt("SoHoaDon"),
                        rs.getInt("TongTien") 
                    };
                    danhSach.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public int getTongDoanhThu(Date ngayBD, Date ngayKT) {
        int tongDoanhThu = 0;
        String sql = "SELECT SUM(TongTien) AS TongDoanhThu FROM HoaDon WHERE NgayLap >= ? AND NgayLap <= ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, ngayBD);
            ps.setDate(2, ngayKT);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tongDoanhThu = rs.getInt("TongDoanhThu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongDoanhThu;
    }

    public ArrayList<Object[]> thongKeSanPham(int thang, int nam) {
        ArrayList<Object[]> danhSach = new ArrayList<>();
        String sql = "SELECT "
                   + "  sp.MaSP, "
                   + "  sp.TenSP, "
                   + "  SUM(ct.SoLuong) AS SoLuongDaBan, "
                   + "  SUM(ct.ThanhTien) AS TongGiaTri "
                   + "FROM "
                   + "  CTHoaDon ct "
                   + "JOIN "
                   + "  HoaDon hd ON ct.MaHD = hd.MaHD "
                   + "JOIN "
                   + "  SanPham sp ON ct.ID = sp.ID "
                   + "WHERE "
                   + "  MONTH(hd.NgayLap) = ? AND YEAR(hd.NgayLap) = ? "
                   + "GROUP BY "
                   + "  sp.MaSP, sp.TenSP "
                   + "ORDER BY "
                   + "  SoLuongDaBan DESC"; 

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, thang);
            ps.setInt(2, nam);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[]{
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("SoLuongDaBan"),
                        rs.getInt("TongGiaTri") 
                    };
                    danhSach.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}