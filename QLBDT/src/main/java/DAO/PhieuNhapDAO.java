package DAO;

import DTO.PhieuNhapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {
    private Connection conn;

    public PhieuNhapDAO() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLBanDongHo;encrypt=false";
            String user = "sa";
            String pass = "123456";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ PhieuNhapDAO: Không thể kết nối SQL Server!");
        }
    }

    // Lấy tất cả phiếu nhập
    public List<PhieuNhapDTO> getAll() {
        List<PhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT MaPN, MaNCC, NgayLap, TongTien FROM PhieuNhap ORDER BY MaPN DESC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                        rs.getInt("MaPN"),
                        rs.getObject("MaNCC") == null ? null : rs.getInt("MaNCC"),
                        rs.getDate("NgayLap"),
                        rs.getDouble("TongTien")
                );
                list.add(pn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy theo ID
    public PhieuNhapDTO getById(int maPN) {
        String sql = "SELECT MaPN, MaNCC, NgayLap, TongTien FROM PhieuNhap WHERE MaPN = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhieuNhapDTO(
                            rs.getInt("MaPN"),
                            rs.getObject("MaNCC") == null ? null : rs.getInt("MaNCC"),
                            rs.getDate("NgayLap"),
                            rs.getDouble("TongTien")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert, trả về generated MaPN (DB id), -1 nếu thất bại
    public int insert(PhieuNhapDTO pn) {
        String sql = "INSERT INTO PhieuNhap (MaNV, MaNCC, NgayLap, TongTien) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, null); // MaNV chưa có => null
            if (pn.getMaNCC() == null) ps.setObject(2, null);
            else ps.setInt(2, pn.getMaNCC());
            ps.setDate(3, pn.getNgayLap());
            ps.setDouble(4, pn.getTongTien());
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
                else return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean update(PhieuNhapDTO pn) {
        String sql = "UPDATE PhieuNhap SET MaNCC=?, NgayLap=?, TongTien=? WHERE MaPN=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (pn.getMaNCC() == null) ps.setObject(1, null);
            else ps.setInt(1, pn.getMaNCC());
            ps.setDate(2, pn.getNgayLap());
            ps.setDouble(3, pn.getTongTien());
            ps.setInt(4, pn.getMaPN());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maPN) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPN = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Không thể xóa (kiểm tra ràng buộc FK với CTPhieuNhap)");
            e.printStackTrace();
            return false;
        }
    }

    // Search: support numeric input (display code or raw id) or MaNCC.
    // public List<PhieuNhapDTO> search(String keyword) {
    //     List<PhieuNhapDTO> list = new ArrayList<>();
    //     if (keyword == null) return list;
    //     keyword = keyword.trim();
    //     // Nếu keyword là số và >= DISPLAY_OFFSET (100001), chuyển về DB id
    //     try {
    //         int val = Integer.parseInt(keyword);
    //         int dbId = (val >= 100001) ? (val - 100000) : val;
    //         String sql = "SELECT MaPN, MaNCC, NgayLap, TongTien FROM PhieuNhap WHERE MaPN = ? OR MaNCC = ? ORDER BY MaPN DESC";
    //         try (PreparedStatement ps = conn.prepareStatement(sql)) {
    //             ps.setInt(1, dbId);
    //             ps.setInt(2, dbId);
    //             try (ResultSet rs = ps.executeQuery()) {
    //                 while (rs.next()) {
    //                     list.add(new PhieuNhapDTO(
    //                             rs.getInt("MaPN"),
    //                             rs.getObject("MaNCC") == null ? null : rs.getInt("MaNCC"),
    //                             rs.getDate("NgayLap"),
    //                             rs.getDouble("TongTien")
    //                     ));
    //                 }
    //             }
    //         }
    //         return list;
    //     } catch (NumberFormatException ignored) {}

    //     // Nếu không phải số, ta có thể mở rộng bằng join với NhaCungCap; tạm trả về tất cả để không gây rỗng
    //     return getAll();
    // }

    public int getNextIdentityEstimate() {
        String sql = "SELECT ISNULL(MAX(MaPN),0) AS MaxId FROM PhieuNhap";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("MaxId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
     public boolean updateTongTien(int maPN, double tongTien) {
        String sql = "UPDATE PhieuNhap SET TongTien = ? WHERE MaPN = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, tongTien);
            ps.setInt(2, maPN);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
