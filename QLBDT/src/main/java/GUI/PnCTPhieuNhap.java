package GUI;

import BUS.CTPhieuNhapBUS;
import BUS.PhieuNhapBUS;
import DTO.CTPhieuNhapDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

public class PnCTPhieuNhap extends JDialog {

    private final int maPN; // MaPN thực trong DB

    private JTextField txtMaPN;
    private JTextField txtID;
    private JTextField txtSoLuong;
    private JTextField txtGiaNhap;
    private JTextField txtThanhTien;
    private JLabel lblTongTien;

    private DefaultTableModel model;
    private JTable table;

    private final CTPhieuNhapBUS ctBus = new CTPhieuNhapBUS();
    private final PhieuNhapBUS pnBus = new PhieuNhapBUS();

    public PnCTPhieuNhap(Frame owner, int maPN) {
        super(owner, "Chi tiết Phiếu Nhập", true); // modal dialog
        this.maPN = maPN;

        setSize(1150, 650);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8, 8));

        Font lblFont = new Font("Segoe UI", Font.PLAIN, 14);

        /* ========== LEFT PANEL (Form nhập/sửa 1 dòng chi tiết) ========== */
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

        JLabel lblMaPN = new JLabel("Mã PN:");
        JLabel lblID = new JLabel("ID (Mã sản phẩm):");
        JLabel lblSL = new JLabel("Số lượng:");
        JLabel lblGia = new JLabel("Giá nhập:");
        JLabel lblThanhTien = new JLabel("Thành tiền:");

        lblMaPN.setFont(lblFont);
        lblID.setFont(lblFont);
        lblSL.setFont(lblFont);
        lblGia.setFont(lblFont);
        lblThanhTien.setFont(lblFont);

        txtMaPN = new JTextField(String.valueOf(maPN));
        txtMaPN.setEditable(false);

        txtID = new JTextField();
        txtSoLuong = new JTextField();
        txtGiaNhap = new JTextField();
        txtThanhTien = new JTextField();
        txtThanhTien.setEditable(false);

        int row = 0;
        g.gridx=0; g.gridy=row; leftPanel.add(lblMaPN,g);
        g.gridx=1; leftPanel.add(txtMaPN,g); row++;

        g.gridx=0; g.gridy=row; leftPanel.add(lblID,g);
        g.gridx=1; leftPanel.add(txtID,g); row++;

        g.gridx=0; g.gridy=row; leftPanel.add(lblSL,g);
        g.gridx=1; leftPanel.add(txtSoLuong,g); row++;

        g.gridx=0; g.gridy=row; leftPanel.add(lblGia,g);
        g.gridx=1; leftPanel.add(txtGiaNhap,g); row++;

        g.gridx=0; g.gridy=row; leftPanel.add(lblThanhTien,g);
        g.gridx=1; leftPanel.add(txtThanhTien,g); row++;

        JButton btnLuu = createClassicButton("Lưu chi tiết", "/icon/save.png");
        JPanel pnlAddHolder = new JPanel();
        pnlAddHolder.setOpaque(false);
        pnlAddHolder.add(btnLuu);

        row++;
        g.gridx=0; g.gridy=row; g.gridwidth=2;
        leftPanel.add(pnlAddHolder,g);

        /* ========== RIGHT PANEL (Bảng chi tiết + tổng tiền) ========== */
        JPanel rightPanel = new JPanel(new BorderLayout(8,8));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "DANH SÁCH CHI TIẾT PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14)));

        String[] cols = {"Mã PN", "ID SP", "Số lượng", "Giá nhập", "Thành tiền"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        rightPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pnlTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlTongTien.add(lblTongTien);
        rightPanel.add(pnlTongTien, BorderLayout.NORTH);

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

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        split.setResizeWeight(0.35);
        split.setDividerLocation(380);
        add(split, BorderLayout.CENTER);

        /* ========== EVENT HANDLERS ========== */

        // Click 1 dòng -> đổ lên form để sửa / xoá
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    txtID.setText(model.getValueAt(r,1).toString());
                    txtSoLuong.setText(model.getValueAt(r,2).toString());
                    txtGiaNhap.setText(model.getValueAt(r,3).toString());
                    txtThanhTien.setText(model.getValueAt(r,4).toString());
                }
            }
        });

        // Lưu chi tiết (thêm mới nếu chưa có; nếu đã có trong table -> báo user dùng Sửa)
        btnLuu.addActionListener(e -> {
            try {
                // đọc input
                int idSP = Integer.parseInt(txtID.getText().trim());
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                int giaNhap = Integer.parseInt(txtGiaNhap.getText().trim());
                int thanhTien = soLuong * giaNhap;
                txtThanhTien.setText(String.valueOf(thanhTien));

                // kiểm tra xem chi tiết này đã tồn tại trong DB/table chưa
                boolean existed = false;
                for (int i=0; i<model.getRowCount(); i++) {
                    int rowMaPN = Integer.parseInt(model.getValueAt(i,0).toString());
                    int rowID   = Integer.parseInt(model.getValueAt(i,1).toString());
                    if (rowMaPN == maPN && rowID == idSP) {
                        existed = true;
                        break;
                    }
                }

                if (existed) {
                    // Nếu đã có, để tránh user hiểu lầm "thêm mới",
                    // ta có thể yêu cầu họ bấm nút Sửa thay vì tự động ghi đè.
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Sản phẩm này đã có trong phiếu. Bạn muốn cập nhật (Sửa) thay vì thêm mới?",
                            "Xác nhận",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean ok = ctBus.updateDetail(maPN, idSP, soLuong, giaNhap);
                        if (!ok) {
                            JOptionPane.showMessageDialog(this,
                                    "Cập nhật thất bại! Kiểm tra lại dữ liệu.",
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        reloadTable();
                        clearInput();
                        JOptionPane.showMessageDialog(this, "Đã cập nhật chi tiết!");
                    }
                } else {
                    // thêm mới vào DB
                    boolean ok = ctBus.addDetail(maPN, idSP, soLuong, giaNhap);
                    if (!ok) {
                        // thường rơi vào đây nếu ID SP không tồn tại -> vi phạm FK
                        JOptionPane.showMessageDialog(this,
                                "Sản phẩm mới chưa có trong hệ thống.\n" +
                        "Vui lòng thêm sản phẩm vào hệ thống trước khi nhập hàng!!!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    reloadTable();
                    clearInput();
                    JOptionPane.showMessageDialog(this, "Đã thêm chi tiết!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập số hợp lệ cho ID SP / Số lượng / Giá nhập!",
                        "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi: " + ex.getMessage(),
                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Nút Sửa: ép cập nhật dòng hiện tại
        btnSua.addActionListener(e -> {
            try {
                int idSP = Integer.parseInt(txtID.getText().trim());
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                int giaNhap = Integer.parseInt(txtGiaNhap.getText().trim());

                boolean ok = ctBus.updateDetail(maPN, idSP, soLuong, giaNhap);
                if (!ok) {
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thất bại! Kiểm tra lại ID SP đã tồn tại trong phiếu.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                reloadTable();
                clearInput();
                JOptionPane.showMessageDialog(this, "Đã cập nhật chi tiết!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập số hợp lệ!",
                        "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi: " + ex.getMessage(),
                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Nút Xóa
        btnXoa.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Chọn 1 dòng trong bảng để xóa.");
                return;
            }
            int idSP = Integer.parseInt(model.getValueAt(r,1).toString());
            int opt = JOptionPane.showConfirmDialog(this,
                    "Xóa sản phẩm ID " + idSP + " khỏi phiếu?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;

            boolean ok = ctBus.deleteDetail(maPN, idSP);
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Xóa thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            reloadTable();
            clearInput();
        });

        // Nút Làm mới
        btnLamMoi.addActionListener(e -> {
            clearInput();
            reloadTable();
        });

        // Nút Nhập (Finalize phiếu)
        btnNhap.addActionListener(e -> {
            // 1. cập nhật tổng tiền vào bảng PhieuNhap
            boolean ok = pnBus.updateTongTienFromDetail(maPN);
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Không cập nhật được tổng tiền phiếu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 2. đóng dialog -> quay về màn hình phiếu nhập
            dispose();
        });

        /* ===== Lần đầu mở form: load dữ liệu từ DB ===== */
        reloadTable();
    }

    // Load lại bảng chi tiết từ DB + cập nhật tổng tiền label
    private void reloadTable() {
        List<CTPhieuNhapDTO> ds = ctBus.getByMaPN(maPN);
        model.setRowCount(0);

        double tong = 0;
        for (CTPhieuNhapDTO ct : ds) {
            model.addRow(new Object[]{
                    ct.getMaPN(),
                    ct.getId(),
                    ct.getSoLuong(),
                    ct.getGiaNhap(),
                    ct.getThanhTien()
            });
            tong += ct.getThanhTien();
        }
        lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VNĐ", tong));
    }

    // clear input bên trái (trừ mã PN)
    private void clearInput() {
        txtID.setText("");
        txtSoLuong.setText("");
        txtGiaNhap.setText("");
        txtThanhTien.setText("");
    }

    // giống style nút bên form chính
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
}
