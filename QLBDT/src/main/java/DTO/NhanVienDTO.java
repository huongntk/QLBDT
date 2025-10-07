package DTO;

public class NhanVienDTO{
    private int maNV;
    private String ho;
    private String ten;
    private String gioiTinh;
    private String soDienThoai;
    private String chucVu;
    private boolean trangThai; 
    public NhanVienDTO() {
    }

    public NhanVienDTO(int maNV, String ho, String ten, String gioiTinh, String soDienThoai, String chucVu, boolean trangThai) {
        this.maNV = maNV;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
    }

  
    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}