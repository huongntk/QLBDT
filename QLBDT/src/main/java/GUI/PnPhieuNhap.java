package GUI;

import BUS.PhieuNhapBUS;
import BUS.CTPhieuNhapBUS;
import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import DTO.PhieuNhapDTO;
import DTO.CTPhieuNhapDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PnPhieuNhap extends JPanel {

    private final PhieuNhapBUS bus = new PhieuNhapBUS();
    private final CTPhieuNhapBUS ctBus = new CTPhieuNhapBUS();
    private final NhaCungCapDAO nccDao = new NhaCungCapDAO();

    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;

    // cache MaNCC -> TenNCC
    private final Map<Integer, String> nccMap = new HashMap<>();

    private final DecimalFormat moneyFormat = new DecimalFormat("#,###");

    public PnPhieuNhap() {
        setLayout(new BorderLayout(8, 8));
        setBackground(Color.WHITE);

        /* ====== TÌM KIẾM ====== */
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlSearch.setBackground(Color.WHITE);
        pnlSearch.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "TÌM KIẾM PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14)));
        JLabel lblSearch = new JLabel("Từ khóa:");
        txtSearch = new JTextField(25);
        JButton btnSearch = createClassicButton("Tìm kiếm", "/icon/search.png");
        pnlSearch.add(lblSearch);
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);
        add(pnlSearch, BorderLayout.NORTH);

        /* ====== DANH SÁCH ====== */
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBackground(Color.WHITE);
        pnlTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "DANH SÁCH PHIẾU NHẬP",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14)));

        String[] cols = {"Mã PN", "MaPN_DB", "Nhà cung cấp", "Ngày lập", "Tổng tiền"};
        model = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        // Ẩn MaPN_DB
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);

        pnlTable.add(new JScrollPane(table), BorderLayout.CENTER);

        /* ====== NÚT HÀNH ĐỘNG ====== */
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        JButton btnThem    = createClassicButton("Thêm",   "/icon/them.png");
        // BỎ NÚT SỬA THEO YÊU CẦU
        JButton btnXoa     = createClassicButton("Xóa",    "/icon/xoa.png");
        JButton btnChiTiet = createClassicButton("Chi tiết","/icon/detail.png");
        JButton btnXuat    = createClassicButton("Xuất",   "/icon/export.png");
        JButton btnLamMoi  = createClassicButton("Làm mới","/icon/undo.png");

        pnlButtons.add(btnThem);
        // pnlButtons.add(btnSua);  // <- removed
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnChiTiet);
        pnlButtons.add(btnXuat);
        pnlButtons.add(btnLamMoi);
        pnlTable.add(pnlButtons, BorderLayout.SOUTH);

        add(pnlTable, BorderLayout.CENTER);

        /* ====== SỰ KIỆN ====== */
        btnThem.addActionListener(e -> showAddDialog());
        // KHÔNG CÒN showEditDialog GẮN VỚI BTN SỬA
        btnXoa.addActionListener(e -> deleteSelected());
        btnLamMoi.addActionListener(e -> { buildNccCache(); loadData(); });
        btnSearch.addActionListener(e -> search());
        btnChiTiet.addActionListener(e -> openDetail());
        btnXuat.addActionListener(e -> exportSelected());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) openDetail();
            }
        });

        buildNccCache();
        loadData();
    }

    /* ================== NCC cache ================== */
    private void buildNccCache() {
        nccMap.clear();
        List<NhaCungCapDTO> ds = nccDao.getAll();
        if (ds == null) return;
        for (NhaCungCapDTO n : ds) {
            int id = n.getMaNCC();
            String ten = n.getTenNCC();
            nccMap.put(id, (ten == null || ten.isEmpty()) ? ("NCC #" + id) : ten);
        }
    }

    /* ================== LOAD BẢNG ================== */
    private void loadData() {
        model.setRowCount(0);
        List<PhieuNhapDTO> list = bus.getAll();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (PhieuNhapDTO pn : list) {
            String displayMaPN = bus.toDisplayCode(pn.getMaPN());
            String tenNCC = (pn.getMaNCC() == null) ? "" : nccMap.getOrDefault(pn.getMaNCC(), String.valueOf(pn.getMaNCC()));
            String formattedMoney = moneyFormat.format(pn.getTongTien()) + " VNĐ";
            model.addRow(new Object[]{
                    displayMaPN,
                    pn.getMaPN(),
                    tenNCC,
                    pn.getNgayLap() == null ? "" : df.format(pn.getNgayLap()),
                    formattedMoney
            });
        }
    }

    /* ================== CHỨC NĂNG ================== */

    // THÊM: KHÔNG CÓ Ô TỔNG TIỀN
    private void showAddDialog() {
        try {
            JPanel p = new JPanel(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(6, 8, 6, 8);
            gc.anchor = GridBagConstraints.WEST;
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.weightx = 1.0;

            String estimate = bus.nextDisplayEstimate();

            JLabel lbMa = new JLabel("Mã PN:");
            JTextField txtMaPN = new JTextField(estimate);
            txtMaPN.setEditable(false);

            JLabel lbNcc = new JLabel("Nhà cung cấp:");
            JComboBox<NhaCungCapDTO> cbo = new JComboBox<>();
            List<NhaCungCapDTO> active = nccDao.getActive();
            if (active != null) for (NhaCungCapDTO n : active) cbo.addItem(n);

            JLabel lbNgay = new JLabel("Ngày lập:");
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
            JTextField txtNgay = new JTextField(today);
            txtNgay.setEditable(false);

            int row = 0;
            gc.gridx = 0; gc.gridy = row; p.add(lbMa, gc);
            gc.gridx = 1;                  p.add(txtMaPN, gc); row++;
            gc.gridx = 0; gc.gridy = row; p.add(lbNcc, gc);
            gc.gridx = 1;                  p.add(cbo, gc);     row++;
            gc.gridx = 0; gc.gridy = row; p.add(lbNgay, gc);
            gc.gridx = 1;                  p.add(txtNgay, gc); row++;

            int opt = JOptionPane.showConfirmDialog(
                    this, p, "Thêm phiếu nhập",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opt == JOptionPane.OK_OPTION) {
                NhaCungCapDTO sel = (NhaCungCapDTO) cbo.getSelectedItem();
                if (sel == null) { JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp."); return; }

                // tạo PN mặc định (TongTien=0), tổng sẽ tính ở CT phiếu
                PhieuNhapDTO pn = bus.createDefault(sel.getMaNCC());
                int newId = bus.add(pn);
                if (newId > 0) {
                    Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
                    PnCTPhieuNhap.showDialog(owner, newId, () -> { buildNccCache(); loadData(); });
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm: " + ex.getMessage());
        }
    }

    // SỬA: KHÔNG CÓ Ô TỔNG TIỀN — GIỮ NGUYÊN TỔNG TIỀN HIỆN TẠI
    private void showEditDialog() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu để sửa."); return; }
        int maPN = (int) model.getValueAt(r, 1);

        try {
            PhieuNhapDTO current = bus.getById(maPN);
            if (current == null) { JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu."); return; }

            JPanel p = new JPanel(new GridLayout(2, 2, 8, 8));

            JComboBox<NhaCungCapDTO> cbo = new JComboBox<>();
            List<NhaCungCapDTO> allNcc = nccDao.getAll();
            if (allNcc != null) for (NhaCungCapDTO n : allNcc) cbo.addItem(n);
            if (current.getMaNCC() != null) {
                for (int i = 0; i < cbo.getItemCount(); i++) {
                    NhaCungCapDTO item = cbo.getItemAt(i);
                    if (item != null && item.getMaNCC() == current.getMaNCC().intValue()) {
                        cbo.setSelectedIndex(i); break;
                    }
                }
            }

            JFormattedTextField txtNgay = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
            String ngayStr = (current.getNgayLap()==null) ? new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())
                                                          : new SimpleDateFormat("yyyy-MM-dd").format(current.getNgayLap());
            txtNgay.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(ngayStr));

            p.add(new JLabel("Nhà cung cấp:")); p.add(cbo);
            p.add(new JLabel("Ngày lập:"));     p.add(txtNgay);

            int opt = JOptionPane.showConfirmDialog(this, p, "Sửa phiếu nhập",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opt == JOptionPane.OK_OPTION) {
                NhaCungCapDTO sel = (NhaCungCapDTO) cbo.getSelectedItem();
                java.sql.Date ngay = new java.sql.Date(((java.util.Date) txtNgay.getValue()).getTime());

                // GIỮ nguyên tổng tiền hiện tại
                PhieuNhapDTO pn = new PhieuNhapDTO(maPN, sel.getMaNCC(), ngay, current.getTongTien());
                if (bus.update(pn)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    buildNccCache();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
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
        int opt = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phiếu " + model.getValueAt(r, 0) + " ?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        if (bus.delete(maPN)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }

    private void search() {
        try {
            String key = txtSearch.getText().trim();
            List<PhieuNhapDTO> res = bus.search(key);
            model.setRowCount(0);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (PhieuNhapDTO pn : res) {
                String displayMaPN = bus.toDisplayCode(pn.getMaPN());
                String tenNCC = (pn.getMaNCC()==null) ? "" : nccMap.getOrDefault(pn.getMaNCC(), String.valueOf(pn.getMaNCC()));
                String formattedMoney = moneyFormat.format(pn.getTongTien()) + " VNĐ";
                model.addRow(new Object[]{
                        displayMaPN,
                        pn.getMaPN(),
                        tenNCC,
                        pn.getNgayLap()==null ? "" : df.format(pn.getNgayLap()),
                        formattedMoney
                });
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
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        PnCTPhieuNhap.showDialog(owner, maPN, () -> { buildNccCache(); loadData(); });
    }

    /* ================== XUẤT PHIẾU NHẬP (CSV) ================== */
    private void exportSelected() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Chọn 1 phiếu để xuất."); return; }
        int maPN = (int) model.getValueAt(r, 1);

        PhieuNhapDTO pn = bus.getById(maPN);
        if (pn == null) { JOptionPane.showMessageDialog(this, "Không lấy được dữ liệu phiếu!"); return; }
        List<CTPhieuNhapDTO> chitiet = ctBus.getByMaPN(maPN);

        String tenNCC = (pn.getMaNCC()==null) ? "" : nccMap.getOrDefault(pn.getMaNCC(), String.valueOf(pn.getMaNCC()));
        String ngay = pn.getNgayLap()==null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(pn.getNgayLap());
        String tong = moneyFormat.format(pn.getTongTien()) + " VNĐ";

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Xuất phiếu nhập (CSV)");
        fc.setSelectedFile(new java.io.File("PhieuNhap_" + maPN + ".csv"));
        int res = fc.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;

        Path out = fc.getSelectedFile().toPath();
        if (!out.toString().toLowerCase().endsWith(".csv")) {
            out = out.resolveSibling(out.getFileName() + ".csv");
        }

        try {
            StringBuilder sb = new StringBuilder();
            sb.append('\uFEFF'); // BOM

            sb.append("PHIẾU NHẬP").append('\n');
            sb.append("Mã PN hiển thị,").append(bus.toDisplayCode(maPN)).append('\n');
            sb.append("MaPN DB,").append(maPN).append('\n');
            sb.append("Nhà cung cấp,").append(escapeCsv(tenNCC)).append('\n');
            sb.append("Ngày lập,").append(ngay).append('\n');
            sb.append("Tổng tiền,").append(tong).append('\n').append('\n');

            sb.append("Mã PN,ID SP,Số lượng,Giá nhập,Thành tiền").append('\n');
            double sum = 0;
            for (CTPhieuNhapDTO ct : chitiet) {
                sum += ct.getThanhTien();
                sb.append(ct.getMaPN()).append(',')
                  .append(ct.getId()).append(',')
                  .append(ct.getSoLuong()).append(',')
                  .append(ct.getGiaNhap()).append(',')
                  .append(ct.getThanhTien()).append('\n');
            }
            sb.append(",,,Tổng cộng,").append((long) sum).append('\n');

            Files.write(out, sb.toString().getBytes(StandardCharsets.UTF_8));
            JOptionPane.showMessageDialog(this, "Đã xuất: " + out.toAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Xuất thất bại: " + ex.getMessage());
        }
    }

    private String escapeCsv(String s) {
        if (s == null) return "";
        boolean needQuote = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        String v = s.replace("\"", "\"\"");
        return needQuote ? "\"" + v + "\"" : v;
    }

    /* ============== Helpers UI ============== */
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
