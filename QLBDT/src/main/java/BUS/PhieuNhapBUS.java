package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

import java.io.FileOutputStream;
import java.util.List;

public class PhieuNhapBUS {
    private PhieuNhapDAO dao = new PhieuNhapDAO();
    private final int DISPLAY_OFFSET = 100000;

    public List<PhieuNhapDTO> getAll() { return dao.getAll(); }

    public PhieuNhapDTO getById(int id) { return dao.getById(id); }

    // Insert: trả về DB id nếu thành công, -1 nếu thất bại
    public int add(PhieuNhapDTO pn) { return dao.insert(pn); }

    public boolean update(PhieuNhapDTO pn) { return dao.update(pn); }

    public boolean delete(int id) { return dao.delete(id); }

    // public List<PhieuNhapDTO> search(String key) { return dao.search(key); }

    public String toDisplayCode(int dbId) { return String.valueOf(DISPLAY_OFFSET + dbId); }

    public String nextDisplayEstimate() {
        int next = dao.getNextIdentityEstimate();
        return toDisplayCode(next);
    }

    public PhieuNhapDTO createDefault(Integer maNCC) {
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        return new PhieuNhapDTO(0, maNCC, today, 0.0);
    }

    
}
