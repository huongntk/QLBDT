/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DTO.PhanQuyen;
import java.sql.*;
import java.util.ArrayList;
public class PhanQuyenDAO {
    public ArrayList<PhanQuyen> getAll(){
        ArrayList<PhanQuyen> list = new ArrayList<>();
        String sql = "SELECT * FROM PhanQuyen";
        try (ResultSet rs = DataProvider.executeQuery(sql)){
            while (rs.next()) {
                list.add(new PhanQuyen(
                    
                    rs.getString("Quyen"),
                    rs.getBoolean("QLNV"),
                    rs.getBoolean("QLSP"),
                    rs.getBoolean("QLKH"),
                    rs.getBoolean("QLHD"),
                    rs.getBoolean("QLPN"),
                    rs.getBoolean("QLThongKe")
                ));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean[] getQuyen(String Quyen) {
        boolean[] dsQuyen = new boolean[7]; // tương ứng 7 checkbox
        String sql = "SELECT * FROM PhanQuyen WHERE Quyen = ?";
        try {
            ResultSet rs = DataProvider.executeQuery(sql, Quyen);
            if (rs.next()) {
                dsQuyen[0] = rs.getBoolean("QLKhuyenMai");
                dsQuyen[1] = rs.getBoolean("QLNhanVien");
                dsQuyen[2] = rs.getBoolean("QLNhapHang");
                dsQuyen[3] = rs.getBoolean("QLBanHang");
                dsQuyen[4] = rs.getBoolean("QLThongKe");
                dsQuyen[5] = rs.getBoolean("QLSanPham");
                dsQuyen[6] = rs.getBoolean("QLKhachHang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsQuyen;
    }
     public ResultSet getDanhSachQuyen() {
        String sql = "SELECT Quyen FROM PhanQuyen";
        return DataProvider.executeQuery(sql);
    }
    
     // Thêm mới hoặc cập nhật quyền
    public boolean insertOrUpdateQuyen(String Quyen, boolean qlBanHang, boolean qlNhapHang,
                                       boolean qlNhanVien, boolean qlSanPham, boolean qlKhachHang,boolean qlKhuyenMai, boolean qlThongKe) {
        try {
            // Nếu quyền đã tồn tại thì cập nhật, ngược lại thì thêm mới
            String checkSql = "SELECT COUNT(*) FROM PhanQuyen WHERE Quyen = ?";
            ResultSet rs = DataProvider.executeQuery(checkSql, Quyen);
            int count = 0;
            if (rs.next()) count = rs.getInt(1);

            String sql;
            if (count > 0) {
                sql = "UPDATE PhanQuyen SET QLBanHang=?, QLNhapHang=?, QLNhanVien=?, QLSanPham=?, QLKhachHang=?, QLThongKe=? WHERE Quyen=?";
                return DataProvider.executeUpdate(sql, qlBanHang, qlNhapHang, qlNhanVien, qlSanPham, qlKhachHang, qlThongKe, Quyen) > 0;
            } else {
                sql = "INSERT INTO PhanQuyen (Quyen, QLBanHang, QLNhapHang, QLNhanVien, QLSanPham, QLKhachHang, QLThongKe) VALUES (?, ?, ?, ?, ?, ?, ?)";
                return DataProvider.executeUpdate(sql, Quyen, qlBanHang, qlNhapHang, qlNhanVien, qlSanPham, qlKhachHang, qlThongKe) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateQuyen(String Quyen, boolean qlBanHang, boolean qlNhapHang,
                           boolean qlNhanVien, boolean qlSanPham, boolean qlKhachHang, boolean qlThongKe) {
        try {
            String sql = "UPDATE PhanQuyen SET QLBanHang=?, QLNhapHang=?, QLNhanVien=?, QLSanPham=?, QLKhachHang=?, QLThongKe=? WHERE Quyen=?";
            return DataProvider.executeUpdate(sql, qlBanHang, qlNhapHang, qlNhanVien, qlSanPham, qlKhachHang, qlThongKe, Quyen) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteQuyen(String Quyen) {
        try {
            String sql = "DELETE FROM PhanQuyen WHERE Quyen = ?";
            return DataProvider.executeUpdate(sql, Quyen) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Cập nhật quyền
    public boolean update(PhanQuyen pq) {
        String sql = "UPDATE INTO PhanQuyen SET QLNhanVien=?, QLSanPham=?, QLKhachHang=?, QLBanHang=?, QLNhapHang=?, QLThongKe=?) WHERE QUYEN=?";
        try {
            int rows = DataProvider.executeUpdate(sql,
              
                pq.isQLNhanVien(),
                pq.isQLSanPham(),
                pq.isQLKhachHang(),
                pq.isQLBanHang(),
                pq.isQLNhapHang(),
                pq.isQLThongKe()     
            );
            return rows >0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
