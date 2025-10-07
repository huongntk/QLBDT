package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PnNhaCungCap extends JFrame {
    private JTextField txtMa, txtTen, txtDiaChi, txtSDT, txtEmail, txtTim;
    private JTable tblNCC;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTim;

    public PnNhaCungCap() {
        setTitle("QUẢN LÝ NHÀ CUNG CẤP");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("QUẢN LÝ NHÀ CUNG CẤP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(0, 123, 255));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setPreferredSize(new Dimension(100, 55));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlMain = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnlMain, BorderLayout.CENTER);

        // ===================== PANEL THÔNG TIN =====================
        JPanel pnlInfo = new JPanel(null);
        pnlInfo.setBorder(new TitledBorder("THÔNG TIN NHÀ CUNG CẤP"));
        pnlMain.add(pnlInfo);

        JLabel lblMa = new JLabel("Mã NCC:");
        lblMa.setBounds(20, 40, 100, 25);
        pnlInfo.add(lblMa);
        txtMa = new JTextField();
        txtMa.setBounds(130, 40, 180, 25);
        pnlInfo.add(txtMa);

        JLabel lblTen = new JLabel("Tên NCC:");
        lblTen.setBounds(20, 80, 100, 25);
        pnlInfo.add(lblTen);
        txtTen = new JTextField();
        txtTen.setBounds(130, 80, 180, 25);
        pnlInfo.add(txtTen);

        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(20, 120, 100, 25);
        pnlInfo.add(lblDC);
        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(130, 120, 180, 25);
        pnlInfo.add(txtDiaChi);

        JLabel lblSDT = new JLabel("SĐT:");
        lblSDT.setBounds(20, 160, 100, 25);
        pnlInfo.add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(130, 160, 180, 25);
        pnlInfo.add(txtSDT);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 200, 100, 25);
        pnlInfo.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(130, 200, 180, 25);
        pnlInfo.add(txtEmail);

        // --- Nút chức năng có icon ---
        btnThem = new JButton("➕ Thêm");
        btnSua = new JButton("✏ Sửa");
        btnXoa = new JButton("🗑 Xóa");
        btnLamMoi = new JButton("🔄 Làm mới");

        btnThem.setBounds(20, 250, 90, 35);
        btnSua.setBounds(120, 250, 90, 35);
        btnXoa.setBounds(220, 250, 90, 35);
        btnLamMoi.setBounds(100, 300, 130, 35);

        pnlInfo.add(btnThem);
        pnlInfo.add(btnSua);
        pnlInfo.add(btnXoa);
        pnlInfo.add(btnLamMoi);

        // ===================== PANEL DANH SÁCH =====================
        JPanel pnlDS = new JPanel(null);
        pnlDS.setBorder(new TitledBorder("DANH SÁCH NHÀ CUNG CẤP"));
        pnlMain.add(pnlDS);

        JLabel lblTim = new JLabel("Tìm NCC:");
        lblTim.setBounds(20, 30, 80, 25);
        pnlDS.add(lblTim);
        txtTim = new JTextField();
        txtTim.setBounds(100, 30, 180, 25);
        pnlDS.add(txtTim);
        btnTim = new JButton("🔍");
        btnTim.setBounds(290, 30, 50, 25);
        pnlDS.add(btnTim);

        tblNCC = new JTable(new DefaultTableModel(
                new String[]{"Mã", "Tên NCC", "Địa chỉ", "SĐT", "Email"}, 0));
        JScrollPane sp = new JScrollPane(tblNCC);
        sp.setBounds(20, 70, 400, 300);
        pnlDS.add(sp);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PnNhaCungCap().setVisible(true));
    }
}
