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
    public boolean doiMatKhau(String user, String oldPass, String newPass) {
        String sql = "UPDATE TaiKhoan SET MatKhau=? WHERE TaiKhoan=? AND MatKhau=?";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPass);
            ps.setString(2, user);
            ps.setString(3, oldPass);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
