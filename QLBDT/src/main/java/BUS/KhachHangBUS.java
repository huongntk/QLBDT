package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;

public class KhachHangBUS {

    private final KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        this.khachHangDAO = new KhachHangDAO();
    }

   
    public ArrayList<KhachHangDTO> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }

   
    public String addKhachHang(KhachHangDTO kh) {
       
        if (kh.getHo().trim().isEmpty()) {
            return "Họ khách hàng không được để trống!";
        }
        if (kh.getTen().trim().isEmpty()) {
            return "Tên khách hàng không được để trống!";
        }
        if (kh.getSoDienThoai().trim().isEmpty()) {
            return "Số điện thoại không được để trống!";
        }

        
        if (!kh.getSoDienThoai().matches("^0\\d{9}$")) {
            return "Số điện thoại không hợp lệ! (Phải đủ 10 số và bắt đầu bằng 0)";
        }
        
      
        if (khachHangDAO.getKhachHangBySdt(kh.getSoDienThoai()) != null) {
            return "Số điện thoại đã tồn tại trong hệ thống!";
        }

       
        if (khachHangDAO.addKhachHang(kh)) {
            return "Thêm khách hàng thành công!";
        } else {
            return "Thêm khách hàng thất bại!";
        }
    }

   
    public String updateKhachHang(KhachHangDTO kh) {
        if (kh.getHo().trim().isEmpty() || kh.getTen().trim().isEmpty()) {
            return "Họ và tên không được để trống!";
        }
        if (!kh.getSoDienThoai().matches("^0\\d{9}$")) {
            return "Số điện thoại không hợp lệ!";
        }
        
       
        KhachHangDTO existingKH = khachHangDAO.getKhachHangBySdt(kh.getSoDienThoai());
        if (existingKH != null && existingKH.getMaKH() != kh.getMaKH()) {
            return "Số điện thoại này đã thuộc về một khách hàng khác!";
        }

        if (khachHangDAO.updateKhachHang(kh)) {
            return "Cập nhật thành công!";
        } else {
            return "Cập nhật thất bại!";
        }
    }


    public String deleteKhachHang(int maKH) {
        if (khachHangDAO.deleteKhachHang(maKH)) {
            return "Xóa khách hàng thành công!";
        } else {
            return "Xóa khách hàng thất bại!";
        }
    }
}