package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class PnCTPhieuNhap extends JFrame {

    private static final AtomicInteger idCounter = new AtomicInteger(1000);

    public PnCTPhieuNhap() {
        setTitle("Chi tiết Phiếu Nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        Font lblFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== LEFT: Thông tin chi tiết phiếu nhập =====
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "THÔNG TIN CHI TIẾT PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 12, 8, 12);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        // ===== Các nhãn và ô nhập =====
        JLabel lblMaPN = new JLabel("Mã PN:");
        JLabel lblID = new JLabel("ID (Mã sản phẩm):");
        JLabel lblSL = new JLabel("Số lượng:");
        JLabel lblGia = new JLabel("Giá bán:");
        JLabel lblThanhTien = new JLabel("Thành tiền:");

        lblMaPN.setFont(lblFont);
        lblID.setFont(lblFont);
        lblSL.setFont(lblFont);
        lblGia.setFont(lblFont);
        lblThanhTien.setFont(lblFont);

        JTextField txtMaPN = new JTextField("PN" + idCounter.incrementAndGet());
        txtMaPN.setEditable(false);
        JTextField txtID = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtGiaBan = new JTextField();
        JTextField txtThanhTien = new JTextField();
        txtThanhTien.setEditable(false);

        int row = 0;
        g.gridx = 0; g.gridy = row; leftPanel.add(lblMaPN, g);
        g.gridx = 1; leftPanel.add(txtMaPN, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblID, g);
        g.gridx = 1; leftPanel.add(txtID, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblSL, g);
        g.gridx = 1; leftPanel.add(txtSoLuong, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblGia, g);
        g.gridx = 1; leftPanel.add(txtGiaBan, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblThanhTien, g);
        g.gridx = 1; leftPanel.add(txtThanhTien, g); row++;

        // ===== Nút Lưu chi tiết =====
        JPanel pnlAddHolder = new JPanel();
        pnlAddHolder.setOpaque(false);
        JButton btnLuu = createClassicButton("Lưu chi tiết", "");
        pnlAddHolder.add(btnLuu);
        row++;
        g.gridx = 0; g.gridy = row; g.gridwidth = 2;
        leftPanel.add(pnlAddHolder, g);

        // ===== RIGHT: Danh sách chi tiết phiếu nhập =====
        JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "DANH SÁCH CHI TIẾT PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14)));

        String[] cols = {"Mã PN", "ID", "Số lượng", "Giá bán", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rightPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Tổng tiền =====
        JPanel pnlTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlTongTien.add(lblTongTien);
        rightPanel.add(pnlTongTien, BorderLayout.NORTH);

        // ===== Nút chức năng phía dưới =====
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        JButton btnSua = createClassicButton("Sửa", "/icon/sua.png");
        JButton btnXoa = createClassicButton("Xóa", "/icon/xoa.png");
        JButton btnLamMoi = createClassicButton("Làm mới", "/icon/undo.png");
        JButton btnNhap = createClassicButton("Nhập", "/icon/boxes.png");
        pnlBottom.add(btnSua);
        pnlBottom.add(btnXoa);
        pnlBottom.add(btnLamMoi);
        pnlBottom.add(btnNhap);
        rightPanel.add(pnlBottom, BorderLayout.SOUTH);

        // ===== Tách hai phần trái - phải =====
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        split.setResizeWeight(0.35);
        split.setDividerLocation(380);
        add(split, BorderLayout.CENTER);

        // ======================================================
        // SỰ KIỆN NÚT LƯU
        // ======================================================
        btnLuu.addActionListener(e -> {
            try {
                String maPN = txtMaPN.getText().trim();
                String id = txtID.getText().trim();
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                double giaBan = Double.parseDouble(txtGiaBan.getText().trim());
                double thanhTien = soLuong * giaBan;

                txtThanhTien.setText(String.valueOf(thanhTien));

                // Thêm vào bảng
                model.addRow(new Object[]{maPN, id, soLuong, giaBan, thanhTien});

                // Cập nhật tổng tiền
                double tong = 0;
                for (int i = 0; i < model.getRowCount(); i++) {
                    tong += (double) model.getValueAt(i, 4);
                }
                lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VNĐ", tong));

                JOptionPane.showMessageDialog(this, "Đã lưu chi tiết phiếu nhập!");

                // Reset input (trừ mã PN)
                txtID.setText("");
                txtSoLuong.setText("");
                txtGiaBan.setText("");
                txtThanhTien.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số lượng và giá bán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnNhap.addActionListener(e -> JOptionPane.showMessageDialog(this, "Thực hiện chức năng nhập phiếu!"));
    }

    // ===== Nút phong cách cổ điển (đồng bộ với PnNhaCungCap) =====
    private JButton createClassicButton(String text, String iconPath) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 36));
        btn.setBackground(UIManager.getColor("Button.background"));
        btn.setBorder(BorderFactory.createLineBorder(new Color(160, 180, 200)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon icon = loadScaledIcon(iconPath, 18, 18);
        if (icon != null) btn.setIcon(icon);
        btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn.setIconTextGap(8);
        return btn;
    }

    // ===== Load icon =====
    private ImageIcon loadScaledIcon(String path, int w, int h) {
        try {
            URL url = getClass().getResource(path);
            if (url == null) return null;
            Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PnCTPhieuNhap().setVisible(true));
    }
}