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
        JPanel pnlAddHolder = new JPanel(new GridBagLayout());
        pnlAddHolder.setOpaque(false);
        JButton btnLuu = createLargeButton("Lưu chi tiết", "/icon/boxes.png",
                new Color(76, 175, 80), new Color(67, 160, 71));
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
        JPanel pnlBottom = new JPanel(new GridBagLayout());
        pnlBottom.setOpaque(false);
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(0, 12, 0, 12);

        JButton btnSua = createWideButton("Sửa", "/icon/sua.png", new Color(33, 150, 243), new Color(25, 118, 210));
        JButton btnXoa = createWideButton("Xóa", "/icon/xoa.png", new Color(244, 67, 54), new Color(211, 47, 47));
        JButton btnLamMoi = createWideButton("Làm mới", "/icon/undo.png", new Color(158, 158, 158), new Color(97, 97, 97));
        JButton btnNhap = createWideButton("Nhập", "/icon/boxes.png", new Color(0, 150, 136), new Color(0, 121, 107));

        b.gridx = 0; pnlBottom.add(btnSua, b);
        b.gridx++; pnlBottom.add(btnXoa, b);
        b.gridx++; pnlBottom.add(btnLamMoi, b);
        b.gridx++; pnlBottom.add(btnNhap, b); // ✅ Nút Nhập nằm bên phải nút Làm mới

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

        // ======================================================
        // SỰ KIỆN NÚT NHẬP (bạn có thể mở form nhập mới hoặc thực thi hành động khác)
        // ======================================================
        btnNhap.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thực hiện chức năng nhập phiếu!");
        });
    }

    // ===== Nút lớn (Lưu) =====
    private JButton createLargeButton(String text, String iconPath, Color bg, Color hover) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(200, 54));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon icon = loadScaledIcon(iconPath, 28, 28);
        if (icon != null) btn.setIcon(icon);
        btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn.setIconTextGap(12);
        addHoverEffect(btn, bg, hover);
        return btn;
    }

    // ===== Nút nhỏ (Sửa, Xóa, Làm mới, Nhập) =====
    private JButton createWideButton(String text, String iconPath, Color bg, Color hover) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(140, 44));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon icon = loadScaledIcon(iconPath, 22, 22);
        if (icon != null) btn.setIcon(icon);
        btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn.setIconTextGap(10);
        addHoverEffect(btn, bg, hover);
        return btn;
    }

    // ===== Hiệu ứng hover =====
    private void addHoverEffect(JButton btn, Color normal, Color hover) {
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });
    }

    // ===== Load icon từ /icon/ =====
    private ImageIcon loadScaledIcon(String resourcePath, int w, int h) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                System.err.println("Không tìm thấy icon: " + resourcePath);
                return null;
            }
            Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PnCTPhieuNhap().setVisible(true));
    }
}
