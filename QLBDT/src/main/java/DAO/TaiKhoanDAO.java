package DAO;

import DTO.TaiKhoan;
import UTIL.DBConnect;
import java.sql.*;

public class TaiKhoanDAO {

    public TaiKhoan login(String user, String pass) {
        TaiKhoan tk = null;
        String sql = "SELECT * FROM TaiKhoan WHERE TaiKhoan=? AND MatKhau=? AND TrangThai=1";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan(
                    rs.getInt("MaNV"),
                    rs.getString("TaiKhoan"),
                    rs.getString("MatKhau"),
                    rs.getString("Quyen"),
                    rs.getInt("TrangThai")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }
    
    public TaiKhoan getTaiKhoanByUsername(String username) {
        String sql = "SELECT * FROM TaiKhoan WHERE taiKhoan = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                    rs.getInt("maNV"),
                    rs.getString("taiKhoan"),
                    rs.getString("matKhau"),
                    rs.getString("quyen"),
                    rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean doiMatKhau(String username, String newPass) {
    String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE taiKhoan = ?";
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newPass);
        ps.setString(2, username);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


}
