package DAO;

import DTO.HoaDon; // Cần tạo DTO này
import DTO.CTHoaDon; // Cần tạo DTO này
import UTIL.DBConnect; // Giả định lớp kết nối CSDL
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class HoaDonDAO {

    // --- Phương thức Tổng hợp Xử lý Giao dịch ---
    /**
     * Thực hiện giao dịch bán hàng: 1. Insert HoaDon, 2. Insert CTHoaDon, 3. Update tồn kho.
     * @param maNV Mã nhân viên lập hóa đơn.
     * @param maKH Mã khách hàng (0 nếu là khách vãng lai).
     * @param tongTien Tổng tiền của hóa đơn.
     * @param chiTietList Danh sách CTHoaDon cần thêm.
     * @return MaHD mới được tạo, hoặc -1 nếu giao dịch thất bại.
     */
    public int thucHienGiaoDich(int maNV, int maKH, double tongTien, ArrayList<CTHoaDon> chiTietList) {
        Connection conn = null;
        int maHD = -1;

        try {
            conn = DBConnect.getConnection();
            // Bắt đầu Transaction: Ngăn không cho commit tự động
            conn.setAutoCommit(false); 

            // 1. INSERT HÓA ĐƠN
            maHD = insertHoaDon(conn, maNV, maKH, tongTien);

            if (maHD <= 0) {
                conn.rollback();
                return -1;
            }

            // 2. INSERT CHI TIẾT HÓA ĐƠN VÀ UPDATE TỒN KHO
            for (CTHoaDon cthd : chiTietList) {
                // a. Insert Chi tiết hóa đơn
                boolean insertCTHDSuccess = insertCTHoaDon(conn, maHD, cthd);
                if (!insertCTHDSuccess) {
                    conn.rollback();
                    return -1;
                }

                // b. Update Số lượng tồn kho (Giảm số lượng)
                boolean updateTonKhoSuccess = updateSoLuongTonKho(conn, cthd.getMaSP(), cthd.getSoLuong());
                if (!updateTonKhoSuccess) {
                    conn.rollback();
                    return -1;
                }
            }

            // 3. COMMIT TRANSACTION
            conn.commit();
            return maHD;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Nếu có lỗi, ROLLBACK Transaction
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;
        } finally {
            // Đặt lại auto commit và đóng kết nối
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // --- Hàm Hỗ trợ 1: Insert HoaDon ---
    private int insertHoaDon(Connection conn, int maNV, int maKH, double tongTien) throws SQLException {
        String sql = "INSERT INTO HoaDon (MaNV, MaKH, NgayLap, TongTien) VALUES (?, ?, GETDATE(), ?)";
        int maHDMoi = -1;

        // Sử dụng Statement.RETURN_GENERATED_KEYS để lấy ID vừa tạo
        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, maNV);
            pst.setInt(2, maKH);
            pst.setBigDecimal(3, BigDecimal.valueOf(tongTien)); // Dùng BigDecimal cho tiền tệ

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        maHDMoi = rs.getInt(1); // Lấy MaHD vừa tạo
                    }
                }
            }
        }
        return maHDMoi;
    }

    // --- Hàm Hỗ trợ 2: Insert CTHoaDon ---
    private boolean insertCTHoaDon(Connection conn, int maHD, CTHoaDon cthd) throws SQLException {
        String sql = "INSERT INTO CTHoaDon (MaHD, SoLuong, GiaBan, ID) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, maHD);
            
            pst.setInt(2, cthd.getSoLuong());
            // Giả định DonGia trong CTHoaDon là kiểu double/BigDecimal
            pst.setBigDecimal(3, BigDecimal.valueOf(cthd.getDonGia())); 
            pst.setInt(4, cthd.getMaSP());
            return pst.executeUpdate() > 0;
        }
    }

    // --- Hàm Hỗ trợ 3: Update Số lượng Tồn kho ---
    private boolean updateSoLuongTonKho(Connection conn, int maSP, int soLuongGiam) throws SQLException {
        String sql = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE ID = ? AND SoLuong >= ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, soLuongGiam); // Số lượng cần giảm
            pst.setInt(2, maSP);
            pst.setInt(3, soLuongGiam); // Điều kiện: đảm bảo tồn kho đủ để trừ

            return pst.executeUpdate() > 0;
        }
    }
}