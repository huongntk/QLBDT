package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.ArrayList;

public class NhanVienBUS {

    private final NhanVienDAO nhanVienDAO;

    public NhanVienBUS() {
        this.nhanVienDAO = new NhanVienDAO();
    }

    public ArrayList<NhanVienDTO> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }

    public String addNhanVien(NhanVienDTO nv) {
        
        if (nv.getHo().trim().isEmpty() || nv.getTen().trim().isEmpty()) {
            return "Họ và tên không được để trống!";
        }
        if (nv.getSoDienThoai().trim().isEmpty()) {
            return "Số điện thoại không được để trống!";
        }
        if (nv.getChucVu().trim().isEmpty()) {
            return "Chức vụ không được để trống!";
        }
        if (!nv.getSoDienThoai().matches("^0\\d{9,10}$")) {
            return "Số điện thoại không hợp lệ! (Phải là 10 hoặc 11 số, bắt đầu bằng 0)";
        }

        
        if (nhanVienDAO.getNhanVienBySdt(nv.getSoDienThoai()) != null) {
            return "Số điện thoại đã tồn tại!";
        }
        
        if (nhanVienDAO.addNhanVien(nv)) {
            return "Thêm nhân viên thành công!";
        }
        return "Thêm nhân viên thất bại!";
    }

    public String updateNhanVien(NhanVienDTO nv) {
        
        if (nv.getHo().trim().isEmpty() || nv.getTen().trim().isEmpty()) {
            return "Họ và tên không được để trống!";
        }
        if (!nv.getSoDienThoai().matches("^0\\d{9,10}$")) {
            return "Số điện thoại không hợp lệ!";
        }

        
        NhanVienDTO existingNV = nhanVienDAO.getNhanVienBySdt(nv.getSoDienThoai());
        if (existingNV != null && existingNV.getMaNV() != nv.getMaNV()) {
            return "Số điện thoại này đã thuộc về một nhân viên khác!";
        }
        
        if (nhanVienDAO.updateNhanVien(nv)) {
            return "Cập nhật thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String deleteNhanVien(int maNV) {
        if (nhanVienDAO.deleteNhanVien(maNV)) {
            return "Xóa nhân viên thành công!";
        }
        return "Xóa nhân viên thất bại!";
    }
}