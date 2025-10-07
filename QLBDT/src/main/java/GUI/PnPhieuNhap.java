package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PnPhieuNhap extends JFrame {
    private JTextField txtTenDT, txtSoLuong, txtDonGia, txtNgayNhap, txtThanhTien, txtTongTien, txtMaPN;
    private JTable tblNCC, tblChiTiet, tblDanhSach;
    private JButton btnThem, btnHuy, btnChuyen, btnNhapHang, btnXoa, btnXuatPhieu, btnTim;

    public PnPhieuNhap() {
        setTitle("QUẢN LÝ PHIẾU NHẬP");
        setSize(1150, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Tiêu đề ---
        JLabel lblTitle = new JLabel("QUẢN LÝ PHIẾU NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(0, 123, 255));
        lblTitle.setPreferredSize(new Dimension(100, 60));
        add(lblTitle, BorderLayout.NORTH);

        // --- Panel chính ---
        JPanel pnlMain = new JPanel(new GridLayout(1, 3, 10, 10));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnlMain, BorderLayout.CENTER);

        // ===================== 1. THÔNG TIN NHẬP HÀNG =====================
        JPanel pnlNhap = new JPanel(null);
        pnlNhap.setBorder(new TitledBorder("THÔNG TIN NHẬP HÀNG"));
        pnlMain.add(pnlNhap);

        JLabel lblTenDT = new JLabel("Tên điện thoại:");
        lblTenDT.setBounds(20, 30, 100, 25);
        pnlNhap.add(lblTenDT);
        txtTenDT = new JTextField();
        txtTenDT.setBounds(130, 30, 200, 25);
        pnlNhap.add(txtTenDT);

        JLabel lblSL = new JLabel("Số lượng:");
        lblSL.setBounds(20, 70, 100, 25);
        pnlNhap.add(lblSL);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(130, 70, 200, 25);
        pnlNhap.add(txtSoLuong);

        JLabel lblDG = new JLabel("Đơn giá:");
        lblDG.setBounds(20, 110, 100, 25);
        pnlNhap.add(lblDG);
        txtDonGia = new JTextField();
        txtDonGia.setBounds(130, 110, 200, 25);
        pnlNhap.add(txtDonGia);

        JLabel lblNgay = new JLabel("Ngày nhập:");
        lblNgay.setBounds(20, 150, 100, 25);
        pnlNhap.add(lblNgay);
        txtNgayNhap = new JTextField("14/06/2024");
        txtNgayNhap.setBounds(130, 150, 200, 25);
        pnlNhap.add(txtNgayNhap);

        JLabel lblNCC = new JLabel("NHÀ CUNG CẤP");
        lblNCC.setBounds(20, 190, 310, 25);
        lblNCC.setOpaque(true);
        lblNCC.setBackground(new Color(0, 180, 255));
        lblNCC.setForeground(Color.WHITE);
        lblNCC.setHorizontalAlignment(SwingConstants.CENTER);
        pnlNhap.add(lblNCC);

        tblNCC = new JTable(new DefaultTableModel(new String[]{"Mã", "Tên NCC", "Địa chỉ", "SĐT", "Email"}, 0));
        JScrollPane spNCC = new JScrollPane(tblNCC);
        spNCC.setBounds(20, 220, 310, 120);
        pnlNhap.add(spNCC);

        JLabel lblThanhTien = new JLabel("Thành tiền:");
        lblThanhTien.setBounds(20, 350, 100, 25);
        pnlNhap.add(lblThanhTien);
        txtThanhTien = new JTextField("0 VND");
        txtThanhTien.setBounds(130, 350, 200, 25);
        pnlNhap.add(txtThanhTien);

        // --- Nút hành động lớn hơn ---
        btnThem = new JButton("➕ Thêm");
        btnHuy = new JButton("✖ Hủy");
        btnChuyen = new JButton("➡ Chuyển");

        btnThem.setBounds(20, 390, 90, 35);
        btnHuy.setBounds(125, 390, 90, 35);
        btnChuyen.setBounds(230, 390, 100, 35);

        pnlNhap.add(btnThem);
        pnlNhap.add(btnHuy);
        pnlNhap.add(btnChuyen);

        // ===================== 2. THÔNG TIN PHIẾU NHẬP =====================
        JPanel pnlCT = new JPanel(null);
        pnlCT.setBorder(new TitledBorder("THÔNG TIN PHIẾU NHẬP"));
        pnlMain.add(pnlCT);

        tblChiTiet = new JTable(new DefaultTableModel(
                new String[]{"Tên điện thoại", "Nhà cung cấp", "Số lượng", "Đơn giá", "Thành tiền"}, 0));
        JScrollPane spCT = new JScrollPane(tblChiTiet);
        spCT.setBounds(20, 30, 330, 250);
        pnlCT.add(spCT);

        JLabel lblTong = new JLabel("Tổng tiền:");
        lblTong.setBounds(20, 290, 100, 25);
        pnlCT.add(lblTong);
        txtTongTien = new JTextField("0 VND");
        txtTongTien.setBounds(120, 290, 230, 25);
        pnlCT.add(txtTongTien);

        btnXoa = new JButton("🗑 Xóa");
        btnNhapHang = new JButton("📦 Nhập hàng");
        btnXoa.setBounds(20, 330, 120, 35);
        btnNhapHang.setBounds(160, 330, 190, 35);
        pnlCT.add(btnXoa);
        pnlCT.add(btnNhapHang);

        // ===================== 3. DANH SÁCH PHIẾU NHẬP =====================
        JPanel pnlDS = new JPanel(null);
        pnlDS.setBorder(new TitledBorder("DANH SÁCH PHIẾU NHẬP"));
        pnlMain.add(pnlDS);

        JLabel lblMaPN = new JLabel("Mã phiếu nhập:");
        lblMaPN.setBounds(20, 30, 120, 25);
        pnlDS.add(lblMaPN);
        txtMaPN = new JTextField();
        txtMaPN.setBounds(140, 30, 140, 25);
        pnlDS.add(txtMaPN);
        btnTim = new JButton("🔍");
        btnTim.setBounds(290, 30, 50, 25);
        pnlDS.add(btnTim);

        tblDanhSach = new JTable(new DefaultTableModel(new String[]{"Mã", "Tên NCC", "Ngày nhập", "Tổng tiền"}, 0));
        JScrollPane spDS = new JScrollPane(tblDanhSach);
        spDS.setBounds(20, 70, 320, 250);
        pnlDS.add(spDS);

        btnXuatPhieu = new JButton("📄 Xuất phiếu");
        btnXuatPhieu.setBounds(100, 330, 170, 35);
        pnlDS.add(btnXuatPhieu);
    }

     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> new PnPhieuNhap().setVisible(true));
     }
}
