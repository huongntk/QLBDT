package BUS;

import DAO.PhanQuyenDAO;
import DTO.PhanQuyen;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PhanQuyenBUS {
    private PhanQuyenDAO pqDAO = new PhanQuyenDAO();
    
    public boolean[] layQuyen(String Quyen) {
        return pqDAO.getQuyen(Quyen);
    }
    
    public ResultSet layDanhSachQuyen() {
        return pqDAO.getDanhSachQuyen();
    }
    
    public ArrayList<PhanQuyen> getAll(){
        return pqDAO.getAll();
    }
    
    public boolean themHoacCapNhatQuyen(String Quyen, boolean qlBanHang, boolean qlNhapHang,
                                        boolean qlNhanVien, boolean qlSanPham, boolean qlKhachHang, boolean qlKhuyenMai, boolean qlThongKe) {
        return pqDAO.insertOrUpdateQuyen(Quyen, qlBanHang, qlNhapHang, qlNhanVien, qlSanPham, qlKhachHang, qlKhuyenMai, qlThongKe);
    }
    
    public boolean capNhatQuyen(String Quyen, boolean qlBanHang, boolean qlNhapHang,
                       boolean qlNhanVien, boolean qlSanPham, boolean qlKhachHang, boolean qlThongKe) {
        return pqDAO.updateQuyen(Quyen, qlBanHang, qlNhapHang, qlNhanVien, qlSanPham, qlKhachHang, qlThongKe);
    }

    public boolean xoaQuyen(String Quyen) {
        return pqDAO.deleteQuyen(Quyen);
    }

    
    public boolean update(PhanQuyen pq){
        return pqDAO.update(pq);
    }
}

