package DAO;

import DTO.NhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {
    private Connection conn;

    public NhaCungCapDAO() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLBanDongHo;encrypt=false";
            String user = "sa";
            String pass = "123456"; // 🔸 đổi nếu bạn đặt khác
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Không thể kết nối SQL Server!");
        }
    }


    public ArrayList<NhaCungCapDTO> getAll() {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                NhaCungCapDTO ncc = new NhaCungCapDTO(
                        rs.getInt("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getBoolean("TrangThai")
                );
                list.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(NhaCungCapDTO ncc) {
        String sql = "INSERT INTO NhaCungCap (TenNCC, DiaChi, SoDienThoai, TrangThai) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setBoolean(4, ncc.isTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhaCungCapDTO ncc) {
        String sql = "UPDATE NhaCungCap SET TenNCC=?, DiaChi=?, SoDienThoai=?, TrangThai=? WHERE MaNCC=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setBoolean(4, ncc.isTrangThai());
            ps.setInt(5, ncc.getMaNCC());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maNCC) {
        String sql = "DELETE FROM NhaCungCap WHERE MaNCC=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNCC);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Không thể xóa (có thể bị khóa ngoại)");
            e.printStackTrace();
            return false;
        }
    }
}
