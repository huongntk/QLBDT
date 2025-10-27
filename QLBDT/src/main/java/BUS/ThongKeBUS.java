package BUS;

import DAO.ThongKeDAO;
import java.sql.Date;
import java.util.ArrayList;

public class ThongKeBUS {

    private ThongKeDAO thongKeDAO;

    public ThongKeBUS() {
        thongKeDAO = new ThongKeDAO();
    }

    public ArrayList<Object[]> thongKeDoanhThu(java.util.Date ngayBD, java.util.Date ngayKT) {
        if (ngayBD == null || ngayKT == null) {
            return new ArrayList<>(); 
        }
        if (ngayKT.before(ngayBD)) {
            return new ArrayList<>(); 
        }
        
        Date sqlNgayBD = new Date(ngayBD.getTime());
        Date sqlNgayKT = new Date(ngayKT.getTime());

        return thongKeDAO.thongKeDoanhThu(sqlNgayBD, sqlNgayKT);
    }

    public int getTongDoanhThu(java.util.Date ngayBD, java.util.Date ngayKT) {
        if (ngayBD == null || ngayKT == null) {
            return 0;
        }
        if (ngayKT.before(ngayBD)) {
            return 0;
        }
        
        Date sqlNgayBD = new Date(ngayBD.getTime());
        Date sqlNgayKT = new Date(ngayKT.getTime());
        
        return thongKeDAO.getTongDoanhThu(sqlNgayBD, sqlNgayKT);
    }
    
    public ArrayList<Object[]> thongKeSanPham(int thang, int nam) {
        if (nam < 2000 || nam > java.time.Year.now().getValue()) {
            return new ArrayList<>();
        }
        
        return thongKeDAO.thongKeSanPham(thang, nam);
    }
}