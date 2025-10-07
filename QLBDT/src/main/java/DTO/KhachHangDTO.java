package DTO;


public class KhachHangDTO{
    private int maKH;
    private String ho;
    private String ten;
    private String gioiTinh;
    private String soDienThoai;
    private int tongChiTieu;
    private boolean tinhTrang; 

    
    public KhachHangDTO() {
    }

    public KhachHangDTO(int maKH, String ho, String ten, String gioiTinh, String soDienThoai, int tongChiTieu, boolean tinhTrang) {
        this.maKH = maKH;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.tongChiTieu = tongChiTieu;
        this.tinhTrang = tinhTrang;
    }

    
    public int getMaKH() {
        return maKH;
    }

    public String getHo() {
        return ho;
    }

    public String getTen() {
        return ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public int getTongChiTieu() {
        return tongChiTieu;
    }

    public boolean isTinhTrang() { // Getter cho kiểu boolean thường bắt đầu bằng 'is'
        return tinhTrang;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setTongChiTieu(int tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "KhachHangDTO{" +
                "maKH=" + maKH +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", tongChiTieu=" + tongChiTieu +
                ", tinhTrang=" + tinhTrang +
                '}';
    }
}