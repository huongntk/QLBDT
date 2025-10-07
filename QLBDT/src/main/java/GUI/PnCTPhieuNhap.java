package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PnCTPhieuNhap extends JFrame {
    private JTextField txtMaPN, txtMaSP, txtSoLuong, txtDonGia, txtThanhTien;
    private JTable tblCTPN;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnXuat;

    public PnCTPhieuNhap() {
        setTitle("CHI TIẾT PHIẾU NHẬP");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Tiêu đề ---
        JLabel lblTitle = new JLabel("CHI TIẾT PHIẾU NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(0, 123, 255));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setPreferredSize(new Dimension(100, 55));
        add(lblTitle, BorderLayout.NORTH);

        // --- Panel chính ---
        JPanel pnlMain = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnlMain, BorderLayout.CENTER);

        // ======================= PANEL THÔNG TIN =======================
        JPanel pnlThongTin = new JPanel(null);
        pnlThongTin.setBorder(new TitledBorder("THÔNG TIN CHI TIẾT"));
        pnlMain.add(pnlThongTin);

        JLabel lblMaPN = new JLabel("Mã phiếu nhập:");
        lblMaPN.setBounds(20, 40, 120, 25);
        pnlThongTin.add(lblMaPN);
        txtMaPN = new JTextField();
        txtMaPN.setBounds(150, 40, 180, 25);
        pnlThongTin.add(txtMaPN);

        JLabel lblMaSP = new JLabel("Mã SP:");
        lblMaSP.setBounds(20, 80, 120, 25);
        pnlThongTin.add(lblMaSP);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(150, 80, 180, 25);
        pnlThongTin.add(txtMaSP);

        JLabel lblSL = new JLabel("Số lượng:");
        lblSL.setBounds(20, 120, 120, 25);
        pnlThongTin.add(lblSL);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(150, 120, 180, 25);
        pnlThongTin.add(txtSoLuong);

        JLabel lblDG = new JLabel("Đơn giá:");
        lblDG.setBounds(20, 160, 120, 25);
        pnlThongTin.add(lblDG);
        txtDonGia = new JTextField();
        txtDonGia.setBounds(150, 160, 180, 25);
        pnlThongTin.add(txtDonGia);

        JLabel lblTT = new JLabel("Thành tiền:");
        lblTT.setBounds(20, 200, 120, 25);
        pnlThongTin.add(lblTT);
        txtThanhTien = new JTextField();
        txtThanhTien.setBounds(150, 200, 180, 25);
        pnlThongTin.add(txtThanhTien);

        // --- Nút chức năng có icon ---
        btnThem = new JButton("➕ Thêm");
        btnSua = new JButton("✏ Sửa");
        btnXoa = new JButton("🗑 Xóa");
        btnLamMoi = new JButton("🔄 Làm mới");

        btnThem.setBounds(20, 250, 90, 35);
        btnSua.setBounds(120, 250, 90, 35);
        btnXoa.setBounds(220, 250, 90, 35);
        btnLamMoi.setBounds(100, 300, 130, 35);

        pnlThongTin.add(btnThem);
        pnlThongTin.add(btnSua);
        pnlThongTin.add(btnXoa);
        pnlThongTin.add(btnLamMoi);

        // ======================= PANEL DANH SÁCH =======================
        JPanel pnlDS = new JPanel(null);
        pnlDS.setBorder(new TitledBorder("DANH SÁCH CHI TIẾT PHIẾU NHẬP"));
        pnlMain.add(pnlDS);

        tblCTPN = new JTable(new DefaultTableModel(
                new String[]{"Mã PN", "Mã SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0));
        JScrollPane sp = new JScrollPane(tblCTPN);
        sp.setBounds(20, 30, 400, 300);
        pnlDS.add(sp);

        btnXuat = new JButton("📄 Xuất danh sách");
        btnXuat.setBounds(140, 350, 170, 35);
        pnlDS.add(btnXuat);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PnCTPhieuNhap().setVisible(true));
    }
}
