package BUS;

import DAO.HoaDonDAO;
import DTO.CTHoaDon;
import java.util.ArrayList;

/**
 * @author [Tên của bạn]
 * Xử lý logic nghiệp vụ liên quan đến Hóa Đơn
 */
public class HoaDonBUS {
    
    private final HoaDonDAO hoaDonDAO;

    public HoaDonBUS() {
        this.hoaDonDAO = new HoaDonDAO();
    }

    /**
     * Thực hiện toàn bộ giao dịch bán hàng (Insert HD, Insert CTHD, Update tồn kho).
     * Phương thức này gọi thẳng đến DAO vì bản thân DAO đã xử lý logic Transaction.
     * @param maNV Mã nhân viên lập hóa đơn.
     * @param maKH Mã khách hàng (0 nếu là khách vãng lai).
     * @param tongTien Tổng tiền của hóa đơn.
     * @param chiTietList Danh sách CTHoaDon cần thêm.
     * @return MaHD mới được tạo, hoặc -1 nếu giao dịch thất bại.
     */
    public int thucHienGiaoDich(int maNV, int maKH, double tongTien, ArrayList<CTHoaDon> chiTietList) {
        
        // Kiểm tra điều kiện nghiệp vụ sơ bộ (nếu cần)
        if (chiTietList == null || chiTietList.isEmpty()) {
            System.err.println("BUS Error: Danh sách chi tiết hóa đơn rỗng.");
            return -1;
        }
        if (maNV <= 0) {
             System.err.println("BUS Error: Mã nhân viên không hợp lệ.");
            return -1;
        }
        
        // Gọi phương thức xử lý giao dịch từ DAO.
        // DAO sẽ chịu trách nhiệm quản lý Connection, commit/rollback, và cập nhật CSDL.
        return hoaDonDAO.thucHienGiaoDich(maNV, maKH, tongTien, chiTietList);
    }
    
    // (Có thể thêm các phương thức khác như getListHoaDon(), getChiTietHoaDon(), v.v...)
}