/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ltd96
 *         ProductDetail entity class
 */
public class ProductDetail {
    // Attributes
    private int MaCTSP;
    private int ID;
    private String DuongKinhMat;
    private String DoDayMat;
    private String ChatLieuVo;
    private String ChatLieuDay;
    private String KieuMat;
    private String Kinh;
    private String BoMay;
    private String NangLuongCo;
    private String ThoiGianTruCoc;
    private String DoChiuNuoc;
    private String ChucNangKhac;
    private String BaoHanh;
    private String MauMatSo;
    private String TrongLuong;

    // Constructor
    public ProductDetail() {
    }

    public ProductDetail(int MaCTSP, int ID, String DuongKinhMat, String DoDayMat, String ChatLieuVo,
            String ChatLieuDay, String KieuMat, String Kinh, String BoMay, String NangLuongCo,
            String ThoiGianTruCoc, String DoChiuNuoc, String ChucNangKhac, String BaoHanh,
            String MauMatSo, String TrongLuong) {
        this.MaCTSP = MaCTSP;
        this.ID = ID;
        this.DuongKinhMat = DuongKinhMat;
        this.DoDayMat = DoDayMat;
        this.ChatLieuVo = ChatLieuVo;
        this.ChatLieuDay = ChatLieuDay;
        this.KieuMat = KieuMat;
        this.Kinh = Kinh;
        this.BoMay = BoMay;
        this.NangLuongCo = NangLuongCo;
        this.ThoiGianTruCoc = ThoiGianTruCoc;
        this.DoChiuNuoc = DoChiuNuoc;
        this.ChucNangKhac = ChucNangKhac;
        this.BaoHanh = BaoHanh;
        this.MauMatSo = MauMatSo;
        this.TrongLuong = TrongLuong;
    }

    // Getter // Setter
    public int getMaCTSP() {
        return MaCTSP;
    }

    public void setMaCTSP(int MaCTSP) {
        this.MaCTSP = MaCTSP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDuongKinhMat() {
        return DuongKinhMat;
    }

    public void setDuongKinhMat(String DuongKinhMat) {
        this.DuongKinhMat = DuongKinhMat;
    }

    public String getDoDayMat() {
        return DoDayMat;
    }

    public void setDoDayMat(String DoDayMat) {
        this.DoDayMat = DoDayMat;
    }

    public String getChatLieuVo() {
        return ChatLieuVo;
    }

    public void setChatLieuVo(String ChatLieuVo) {
        this.ChatLieuVo = ChatLieuVo;
    }

    public String getChatLieuDay() {
        return ChatLieuDay;
    }

    public void setChatLieuDay(String ChatLieuDay) {
        this.ChatLieuDay = ChatLieuDay;
    }

    public String getKieuMat() {
        return KieuMat;
    }

    public void setKieuMat(String KieuMat) {
        this.KieuMat = KieuMat;
    }

    public String getKinh() {
        return Kinh;
    }

    public void setKinh(String Kinh) {
        this.Kinh = Kinh;
    }

    public String getBoMay() {
        return BoMay;
    }

    public void setBoMay(String BoMay) {
        this.BoMay = BoMay;
    }

    public String getNangLuongCo() {
        return NangLuongCo;
    }

    public void setNangLuongCo(String NangLuongCo) {
        this.NangLuongCo = NangLuongCo;
    }

    public String getThoiGianTruCoc() {
        return ThoiGianTruCoc;
    }

    public void setThoiGianTruCoc(String ThoiGianTruCoc) {
        this.ThoiGianTruCoc = ThoiGianTruCoc;
    }

    public String getDoChiuNuoc() {
        return DoChiuNuoc;
    }

    public void setDoChiuNuoc(String DoChiuNuoc) {
        this.DoChiuNuoc = DoChiuNuoc;
    }

    public String getChucNangKhac() {
        return ChucNangKhac;
    }

    public void setChucNangKhac(String ChucNangKhac) {
        this.ChucNangKhac = ChucNangKhac;
    }

    public String getBaoHanh() {
        return BaoHanh;
    }

    public void setBaoHanh(String BaoHanh) {
        this.BaoHanh = BaoHanh;
    }

    public String getMauMatSo() {
        return MauMatSo;
    }

    public void setMauMatSo(String MauMatSo) {
        this.MauMatSo = MauMatSo;
    }

    public String getTrongLuong() {
        return TrongLuong;
    }

    public void setTrongLuong(String TrongLuong) {
        this.TrongLuong = TrongLuong;
    }
}
