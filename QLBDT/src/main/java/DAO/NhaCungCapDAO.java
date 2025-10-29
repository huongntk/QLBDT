package DAO;

import DTO.NhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {

    //Lấy toàn bộ danh sách nhà cung cấp
    public ArrayList<NhaCungCapDTO> getAll() {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";

        try (ResultSet rs = DataProvider.executeQuery(sql)) {
            while (rs != null && rs.next()) {
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
            System.err.println("❌ Lỗi khi lấy danh sách nhà cung cấp:");
            e.printStackTrace();
        }
        return list;
    }

    // Thêm nhà cung cấp
    public boolean insert(NhaCungCapDTO ncc) {
        String sql = "INSERT INTO NhaCungCap (TenNCC, DiaChi, SoDienThoai, TrangThai) VALUES (?, ?, ?, ?)";
        int rows = DataProvider.executeUpdate(sql,
                ncc.getTenNCC(),
                ncc.getDiaChi(),
                ncc.getSoDienThoai(),
                ncc.isTrangThai()
        );
        return rows > 0;
    }

    // cập nhật thông tin nhà cung cấp
    public boolean update(NhaCungCapDTO ncc) {
        String sql = "UPDATE NhaCungCap SET TenNCC=?, DiaChi=?, SoDienThoai=?, TrangThai=? WHERE MaNCC=?";
        int rows = DataProvider.executeUpdate(sql,
                ncc.getTenNCC(),
                ncc.getDiaChi(),
                ncc.getSoDienThoai(),
                ncc.isTrangThai(),
                ncc.getMaNCC()
        );
        return rows > 0;
    }

    // Xóa nhà cung cấp (đề xuất dùng soft delete để tránh lỗi FK)
    public boolean delete(int maNCC) {
        //  Nếu muốn “ẩn” thay vì xóa hẳn: đổi thành câu UPDATE TrangThai=0
        String sql = "DELETE FROM NhaCungCap WHERE MaNCC=?";
        int rows = DataProvider.executeUpdate(sql, maNCC);
        return rows > 0;
    }

    // Lấy 1 nhà cung cấp theo ID
    public NhaCungCapDTO getById(int maNCC) {
        String sql = "SELECT * FROM NhaCungCap WHERE MaNCC=?";
        try (ResultSet rs = DataProvider.executeQuery(sql, maNCC)) {
            if (rs != null && rs.next()) {
                return new NhaCungCapDTO(
                        rs.getInt("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getBoolean("TrangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy thông tin NCC theo ID");
            e.printStackTrace();
        }
        return null;
    }
}
