package GUI;

import BUS.CTPhieuNhapBUS;
import BUS.PhieuNhapBUS;
import DAO.SanphamDAO;
import DTO.CTPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import DTO.Product;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

public class PnCTPhieuNhap extends JDialog {

    private final int maPN;

    private JTextField txtMaPN;
    private JComboBox<Product> cboSanPham;
    private JTextField txtSoLuong;
    private JTextField txtGiaNhap;
    private JTextField txtThanhTien;
    private JLabel lblTongTien;

    private JButton btnLuu, btnSua, btnXoa, btnLamMoi, btnNhap;

    private DefaultTableModel model;
    private JTable table;

    private final CTPhieuNhapBUS ctBus = new CTPhieuNhapBUS();
    private final PhieuNhapBUS pnBus = new PhieuNhapBUS();
    private final SanphamDAO spDao = new SanphamDAO();

    private final DecimalFormat moneyFmt = new DecimalFormat("#,###");

    // trạng thái khóa hiện tại
    private boolean readOnly = false;

    public static void showDialog(Frame owner, int maPN, Runnable onClose) {
        PnCTPhieuNhap dlg = new PnCTPhieuNhap(owner, maPN);
        dlg.setVisible(true);
        if (onClose != null) onClose.run();
    }

    public PnCTPhieuNhap(Frame owner, int maPN) {
        super(owner, "Chi tiết Phiếu Nhập", true);
        this.maPN = maPN;

        setSize(1150, 650);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8, 8));

        Font lblFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== LEFT =====
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "THÔNG TIN CHI TIẾT PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 12, 8, 12);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        JLabel lblMaPN = new JLabel("Mã PN:");
        JLabel lblSP = new JLabel("Sản phẩm:");
        JLabel lblSL = new JLabel("Số lượng:");
        JLabel lblGia = new JLabel("Giá nhập:");
        JLabel lblThanhTien = new JLabel("Thành tiền:");

        lblMaPN.setFont(lblFont);
        lblSP.setFont(lblFont);
        lblSL.setFont(lblFont);
        lblGia.setFont(lblFont);
        lblThanhTien.setFont(lblFont);

        txtMaPN = new JTextField(String.valueOf(maPN));
        txtMaPN.setEditable(false);

        cboSanPham = new JComboBox<>();
        txtSoLuong = new JTextField();
        txtGiaNhap = new JTextField();
        txtThanhTien = new JTextField();
        txtThanhTien.setEditable(false);

        int row = 0;
        g.gridx = 0; g.gridy = row; leftPanel.add(lblMaPN, g);
        g.gridx = 1; leftPanel.add(txtMaPN, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblSP, g);
        g.gridx = 1; leftPanel.add(cboSanPham, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblSL, g);
        g.gridx = 1; leftPanel.add(txtSoLuong, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblGia, g);
        g.gridx = 1; leftPanel.add(txtGiaNhap, g); row++;

        g.gridx = 0; g.gridy = row; leftPanel.add(lblThanhTien, g);
        g.gridx = 1; leftPanel.add(txtThanhTien, g); row++;

        btnLuu = createClassicButton("Lưu chi tiết", "/icon/save.png");
        JPanel pnlAddHolder = new JPanel();
        pnlAddHolder.setOpaque(false);
        pnlAddHolder.add(btnLuu);

        row++;
        g.gridx = 0; g.gridy = row; g.gridwidth = 2;
        leftPanel.add(pnlAddHolder, g);

        // ===== RIGHT =====
        JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "DANH SÁCH CHI TIẾT PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14)));

        String[] cols = {"Mã PN", "ID SP", "Sản phẩm", "Số lượng", "Giá nhập", "Thành tiền"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Ẩn cột ID SP
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);

        rightPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pnlTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlTongTien.add(lblTongTien);
        rightPanel.add(pnlTongTien, BorderLayout.NORTH);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        btnSua = createClassicButton("Sửa", "/icon/sua.png");
        btnXoa = createClassicButton("Xóa", "/icon/xoa.png");
        btnLamMoi = createClassicButton("Làm mới", "/icon/undo.png");
        btnNhap = createClassicButton("Nhập", "/icon/boxes.png");
        pnlBottom.add(btnSua);
        pnlBottom.add(btnXoa);
        pnlBottom.add(btnLamMoi);
        pnlBottom.add(btnNhap);
        rightPanel.add(pnlBottom, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        split.setResizeWeight(0.35);
        split.setDividerLocation(380);
        add(split, BorderLayout.CENTER);

        // ===== EVENTS =====

        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (readOnly) return; // không cho sửa khi đã khóa
                int r = table.getSelectedRow();
                if (r >= 0) {
                    int idSP = Integer.parseInt(model.getValueAt(r, 1).toString());
                    selectProductInCombo(idSP);
                    txtSoLuong.setText(model.getValueAt(r, 3).toString());
                    txtGiaNhap.setText(model.getValueAt(r, 4).toString());
                    txtThanhTien.setText(model.getValueAt(r, 5).toString());
                }
            }
        });

        // Lưu chi tiết
        btnLuu.addActionListener(e -> {
            try {
                Product sp = (Product) cboSanPham.getSelectedItem();
                if (sp == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
                    return;
                }
                int idSP = sp.getID();
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                int giaNhap = parseMoney(txtGiaNhap.getText());  // <-- dùng parseMoney
                int thanhTien = soLuong * giaNhap;
                txtThanhTien.setText(formatMoney(thanhTien));     // <-- hiển thị dạng tiền tệ

                boolean existed = false;
                for (int i = 0; i < model.getRowCount(); i++) {
                    int rowMaPN = Integer.parseInt(model.getValueAt(i, 0).toString());
                    int rowID = Integer.parseInt(model.getValueAt(i, 1).toString());
                    if (rowMaPN == maPN && rowID == idSP) { existed = true; break; }
                }

                if (existed) {
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Sản phẩm đã có trong phiếu. Bạn muốn cập nhật (Sửa) thay vì thêm mới?",
                            "Xác nhận", JOptionPane.YES_NO_OPTION);
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
                    boolean ok = ctBus.addDetail(maPN, idSP, soLuong, giaNhap);
                    if (!ok) {
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
                        "Vui lòng nhập số hợp lệ cho Số lượng / Giá nhập!",
                        "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi: " + ex.getMessage(),
                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sửa chi tiết
        btnSua.addActionListener(e -> {
            try {
                Product sp = (Product) cboSanPham.getSelectedItem();
                if (sp == null) { JOptionPane.showMessageDialog(this, "Chọn sản phẩm!"); return; }
                int idSP = sp.getID();
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                int giaNhap = parseMoney(txtGiaNhap.getText());   // <-- dùng parseMoney

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
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!",
                        "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(),
                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Xóa chi tiết
        btnXoa.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) { JOptionPane.showMessageDialog(this, "Chọn 1 dòng để xóa."); return; }
            int idSP = Integer.parseInt(model.getValueAt(r, 1).toString());
            int opt = JOptionPane.showConfirmDialog(this,
                    "Xóa sản phẩm ID " + idSP + " khỏi phiếu?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;

            boolean ok = ctBus.deleteDetail(maPN, idSP);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            reloadTable();
            clearInput();
        });

        // Làm mới
        btnLamMoi.addActionListener(e -> {
            clearInput();
            reloadTable();
        });

        // Nhập (Finalize) — khóa form sau khi nhập
        btnNhap.addActionListener(e -> {
            boolean ok = pnBus.updateTongTienFromDetail(maPN);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Không cập nhật được tổng tiền phiếu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // nếu dự án có cột/flag "DaNhap"
            try { pnBus.markFinalized(maPN); } catch (Throwable ignore) { /* có thể chưa có cột */ }

            reloadTable();
            maybeLockIfFinalized();
            JOptionPane.showMessageDialog(this, "Nhập phiếu thành công!");
        });

        // ===== initial load =====
        loadProductsForSupplier();
        reloadTable();
        maybeLockIfFinalized();
    }

    /* ===== Helper: quyết định khóa form ===== */
    private void maybeLockIfFinalized() {
        boolean hasItems = !ctBus.getByMaPN(maPN).isEmpty();
        boolean finalizedFlag = false;
        try { finalizedFlag = pnBus.isFinalized(maPN); } catch (Throwable ignore) { /* có thể chưa có hàm/flag */ }

        boolean finalizedFallback = false;
        if (!finalizedFlag) {
            PhieuNhapDTO pn = pnBus.getById(maPN);
            finalizedFallback = hasItems && pn != null && pn.getTongTien() > 0;
        }
        setReadOnly(hasItems && (finalizedFlag || finalizedFallback));
    }

    /* ===== nạp danh sách SP theo NCC của phiếu ===== */
    private void loadProductsForSupplier() {
        PhieuNhapDTO pn = pnBus.getById(maPN);
        cboSanPham.removeAllItems();
        if (pn == null || pn.getMaNCC() == null) return;

        List<Product> products = spDao.getByNccActive(pn.getMaNCC());
        for (Product p : products) cboSanPham.addItem(p); // Product.toString() trả về TenSP
        cboSanPham.setSelectedIndex(-1);
    }

    private void selectProductInCombo(int idSP) {
        for (int i = 0; i < cboSanPham.getItemCount(); i++) {
            Product sp = cboSanPham.getItemAt(i);
            if (sp != null && sp.getID() == idSP) {
                cboSanPham.setSelectedIndex(i);
                return;
            }
        }
        cboSanPham.setSelectedIndex(-1);
    }

    // nạp bảng + cập nhật tổng tiền + có thể khóa
    private void reloadTable() {
        List<CTPhieuNhapDTO> ds = ctBus.getByMaPN(maPN);
        model.setRowCount(0);

        double tong = 0;
        for (CTPhieuNhapDTO ct : ds) {
            String tenSP = "";
            Product sp = spDao.getOneById(ct.getId());
            if (sp != null) tenSP = sp.getTenSP();

            String giaNhapStr   = formatMoney(ct.getGiaNhap());
            String thanhTienStr = formatMoney(ct.getThanhTien());

            model.addRow(new Object[]{
                    ct.getMaPN(),
                    ct.getId(),
                    tenSP,
                    ct.getSoLuong(),
                    giaNhapStr,
                    thanhTienStr
            });
            tong += ct.getThanhTien();
        }
        lblTongTien.setText("Tổng tiền: " + moneyFmt.format(tong) + " VNĐ");

        // cập nhật trạng thái khóa mỗi lần reload
        maybeLockIfFinalized();
    }

    private void setReadOnly(boolean ro) {
        this.readOnly = ro;
        cboSanPham.setEnabled(!ro);
        txtSoLuong.setEditable(!ro);
        txtGiaNhap.setEditable(!ro);

        btnLuu.setEnabled(!ro);
        btnSua.setEnabled(!ro);
        btnXoa.setEnabled(!ro);
        btnLamMoi.setEnabled(!ro);
        btnNhap.setEnabled(!ro);
    }

    private void clearInput() {
        cboSanPham.setSelectedIndex(-1);
        txtSoLuong.setText("");
        txtGiaNhap.setText("");
        txtThanhTien.setText("");
    }

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

    // ====== HÀM HỖ TRỢ ĐỊNH DẠNG TIỀN ======
    private String formatMoney(long value) {
        return moneyFmt.format(value);
    }

    private int parseMoney(String text) throws NumberFormatException {
        if (text == null) return 0;
        String cleaned = text.replaceAll("[^0-9]", ""); // bỏ tất cả ký tự không phải số
        if (cleaned.isEmpty()) return 0;
        return Integer.parseInt(cleaned);
    }
}
