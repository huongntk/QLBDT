package GUI;

import BUS.PhieuNhapBUS;
import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import DTO.PhieuNhapDTO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

public class PnPhieuNhap extends JFrame {
    private PhieuNhapBUS bus = new PhieuNhapBUS();
    private NhaCungCapDAO nccDao = new NhaCungCapDAO();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;

    public PnPhieuNhap() {
        setTitle("Quản lý Phiếu Nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        Font lblFont = new Font("Segoe UI", Font.PLAIN, 14);

        // TOP: search
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlSearch.setBackground(Color.WHITE);
        txtSearch = new JTextField(25);
        txtSearch.setFont(lblFont);
        JButton btnSearch = createWideButton("Tìm kiếm", "/icon/search.png",
                new Color(33, 150, 243), new Color(25, 118, 210));
        pnlSearch.add(new JLabel("Từ khóa:"));
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);
        add(pnlSearch, BorderLayout.NORTH);

        // CENTER: table
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBackground(Color.WHITE);
        pnlTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "DANH SÁCH PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14)));

        String[] cols = {"Mã PN", "MaPN_DB", "Mã NCC", "Ngày lập", "Tổng tiền"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        // hide db id column
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);

        pnlTable.add(new JScrollPane(table), BorderLayout.CENTER);

        // BOTTOM: buttons
        JPanel pnlButtons = new JPanel(new GridBagLayout());
        pnlButtons.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 12, 0, 12);

        JButton btnThem = createWideButton("Thêm", "/icon/them.png",
                new Color(76, 175, 80), new Color(67, 160, 71));
        JButton btnSua = createWideButton("Sửa", "/icon/sua.png",
                new Color(33, 150, 243), new Color(25, 118, 210));
        JButton btnXoa = createWideButton("Xóa", "/icon/xoa.png",
                new Color(244, 67, 54), new Color(211, 47, 47));
        JButton btnChiTiet = createWideButton("Chi tiết", "/icon/detail.png",
                new Color(255, 152, 0), new Color(245, 124, 0));
        JButton btnLamMoi = createWideButton("Làm mới", "/icon/undo.png",
                new Color(158, 158, 158), new Color(97, 97, 97));

        c.gridx = 0; pnlButtons.add(btnThem, c);
        c.gridx = 1; pnlButtons.add(btnSua, c);
        c.gridx = 2; pnlButtons.add(btnXoa, c);
        c.gridx = 3; pnlButtons.add(btnChiTiet, c);
        c.gridx = 4; pnlButtons.add(btnLamMoi, c);

        pnlTable.add(pnlButtons, BorderLayout.SOUTH);
        add(pnlTable, BorderLayout.CENTER);

        // Events
        btnThem.addActionListener(e -> showAddDialog());
        btnSua.addActionListener(e -> showEditDialog());
        btnXoa.addActionListener(e -> deleteSelected());
        btnLamMoi.addActionListener(e -> loadData());
        btnSearch.addActionListener(e -> search());
        btnChiTiet.addActionListener(e -> openDetail());
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) openDetail();
            }
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<PhieuNhapDTO> list = bus.getAll();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (PhieuNhapDTO pn : list) {
            String disp = bus.toDisplayCode(pn.getMaPN());
            model.addRow(new Object[]{
                    disp,
                    pn.getMaPN(),
                    pn.getMaNCC(),
                    pn.getNgayLap() == null ? "" : df.format(pn.getNgayLap()),
                    String.format("%.0f", pn.getTongTien())
            });
        }
    }

    private void showAddDialog() {
        try {
            JPanel p = new JPanel(new GridLayout(4,2,8,8));
            String estimate = bus.nextDisplayEstimate();
            JTextField txtMaPN = new JTextField(estimate);
            txtMaPN.setEditable(false);

            JComboBox<NhaCungCapDTO> cbo = new JComboBox<>();
            for (NhaCungCapDTO n : nccDao.getAll()) cbo.addItem(n);

            JTextField txtNgay = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
            txtNgay.setEditable(false);

            JTextField txtTong = new JTextField("0");
            txtTong.setEditable(false);

            p.add(new JLabel("Mã PN (hiển thị):")); p.add(txtMaPN);
            p.add(new JLabel("Nhà cung cấp:")); p.add(cbo);
            p.add(new JLabel("Ngày lập:")); p.add(txtNgay);
            p.add(new JLabel("Tổng tiền:")); p.add(txtTong);

            int opt = JOptionPane.showConfirmDialog(this, p, "Thêm phiếu nhập", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opt == JOptionPane.OK_OPTION) {
                NhaCungCapDTO sel = (NhaCungCapDTO) cbo.getSelectedItem();
                if (sel == null) { JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp."); return; }

                PhieuNhapDTO pn = bus.createDefault(sel.getMaNCC());
                int newId = bus.add(pn);
                if (newId > 0) {
                    JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công. Mã hiển thị: " + bus.toDisplayCode(newId));
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thất bại!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm: " + ex.getMessage());
        }
    }

    private void showEditDialog() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu để sửa."); return; }
        int maPN = (int) model.getValueAt(r, 1);
        try {
            String ngayStr = (String) model.getValueAt(r, 3);
            String tongStr = (String) model.getValueAt(r, 4);
            Integer curMaNCC = (Integer) model.getValueAt(r, 2);

            JPanel p = new JPanel(new GridLayout(3,2,8,8));
            JComboBox<NhaCungCapDTO> cbo = new JComboBox<>();
            for (NhaCungCapDTO n : nccDao.getAll()) cbo.addItem(n);
            // select current
            for (int i=0;i<cbo.getItemCount();i++) {
                if (cbo.getItemAt(i).getMaNCC() == (curMaNCC == null ? -1 : curMaNCC)) { cbo.setSelectedIndex(i); break; }
            }
            JFormattedTextField txtNgay = new JFormattedTextField(new java.text.SimpleDateFormat("yyyy-MM-dd"));
            txtNgay.setValue(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(ngayStr));
            JTextField txtTong = new JTextField(tongStr);

            p.add(new JLabel("Nhà cung cấp:")); p.add(cbo);
            p.add(new JLabel("Ngày lập:")); p.add(txtNgay);
            p.add(new JLabel("Tổng tiền:")); p.add(txtTong);

            int opt = JOptionPane.showConfirmDialog(this, p, "Sửa phiếu nhập", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opt == JOptionPane.OK_OPTION) {
                NhaCungCapDTO sel = (NhaCungCapDTO) cbo.getSelectedItem();
                java.sql.Date ngay = new java.sql.Date(((java.util.Date)txtNgay.getValue()).getTime());
                double tong = Double.parseDouble(txtTong.getText().replaceAll(",", "").trim());

                PhieuNhapDTO pn = new PhieuNhapDTO(maPN, sel.getMaNCC(), ngay, tong);
                if (bus.update(pn)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
                    loadData();
                } else JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi sửa: " + ex.getMessage());
        }
    }

    private void deleteSelected() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu để xóa."); return; }
        int maPN = (int) model.getValueAt(r, 1);
        int opt = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phiếu " + model.getValueAt(r,0) + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;
        if (bus.delete(maPN)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công.");
            loadData();
        } else JOptionPane.showMessageDialog(this, "Xóa thất bại (có thể có chi tiết).");
    }

    private void search() {
        try {
            String key = txtSearch.getText().trim();
            List<PhieuNhapDTO> res = bus.search(key);
            model.setRowCount(0);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (PhieuNhapDTO pn : res) {
                model.addRow(new Object[]{bus.toDisplayCode(pn.getMaPN()), pn.getMaPN(), pn.getMaNCC(), pn.getNgayLap()==null?"":df.format(pn.getNgayLap()), String.format("%.0f", pn.getTongTien())});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + ex.getMessage());
        }
    }

    private void openDetail() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu để xem chi tiết."); return; }
        int maPN = (int) model.getValueAt(r, 1);
        // Mở form CTPhieuNhap (nếu đã có), tạm placeholder:
        // new PnCTPhieuNhap(maPN).setVisible(true); // khi bạn implement CT form
        JOptionPane.showMessageDialog(this, "Mở chi tiết phiếu (MaPN_DB = " + maPN + "). (CTPhieuNhap sẽ làm sau)");
    }

    // UI helpers (same as bạn)
    private JButton createWideButton(String text, String iconPath, Color bg, Color hover) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(150, 44));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (iconPath != null) {
            ImageIcon icon = loadScaledIcon(iconPath, 22, 22);
            if (icon != null) btn.setIcon(icon);
        }
        btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn.setIconTextGap(10);
        addHoverEffect(btn, bg, hover);
        return btn;
    }

    private void addHoverEffect(JButton btn, Color normal, Color hover) {
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });
    }

    private ImageIcon loadScaledIcon(String resourcePath, int w, int h) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) return null;
            Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PnPhieuNhap f = new PnPhieuNhap();
            f.setVisible(true);
        });
    }
}
