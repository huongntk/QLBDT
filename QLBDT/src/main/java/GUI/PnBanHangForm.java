package GUI;

import DAO.SanphamDAO;
import DTO.Product;
import UTIL.Auth;
import java.util.ArrayList;
import java.awt.Image;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.*;
import UTIL.DBConnect;

public class PnBanHangForm extends javax.swing.JPanel {
    
    private int soLuongTonHienTai = 0;

    public PnBanHangForm() {
        initComponents();
        
        hienThiTatCaSanPham();
        
        javax.swing.table.JTableHeader header = tblDanhSachSP.getTableHeader() ;
        ((javax.swing.table.DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(javax.swing.JLabel.CENTER);
            header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        
        header = tblGioHang.getTableHeader();        
        ((javax.swing.table.DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(javax.swing.JLabel.CENTER);
            header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        
        
        loadDanhSachSanPham();
            
        btnLamMoi.addActionListener(e -> hienThiTatCaSanPham());
        btnThem.addActionListener(e -> themVaoGioHang());
        

        btnXoa.addActionListener(e -> xoaSanPhamKhoiGioHang());
//        btnXacNhan.addActionListener(e -> xacNhanMuaHang());
        
        btnTim.addActionListener(e -> {
            String tenSP = txtTimKiem.getText().trim();
            timKiemSanPhamTheoTen(tenSP);
        });

        tblDanhSachSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            hienThongTinSanPham();
        }
        });

        txtMaNV.setText(String.valueOf(Auth.maNV));
        txtMaNV.setEditable(false); // Không cho sửa
        
//        setupTableRenderer();
    }
    
    private void hienThiTatCaSanPham() {
        SanphamDAO dao = new SanphamDAO();
        ArrayList<Product> list = dao.getALL();  // Lấy toàn bộ sản phẩm từ DB

        DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trên bảng

        for (Product p : list) {
            model.addRow(new Object[]{
                p.getID(),
                p.getTenSP(),
                p.getThuongHieu(),
                p.getGiaBan(),
                p.getSoLuong(),
                p.getXuatXu()
            });
        }   
    }
    
    private void loadDanhSachSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
        model.setRowCount(0);

        String sql = "SELECT ID, TenSP, Loai, DonGia, MoTa FROM SanPham WHERE TrangThai = 1";
        try (Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ID"),
                    rs.getString("TenSP"),
                    rs.getInt("Loai"),
                    rs.getDouble("DonGia"),
                    rs.getString("MoTa")
                });
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void timKiemSanPhamTheoTen(String tenSP) {
        SanphamDAO dao = new SanphamDAO();
        ArrayList<Product> list = dao.getByName(tenSP);
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
        model.setRowCount(0);

        for (Product p : list) {
            model.addRow(new Object[]{
                p.getID(), p.getTenSP(), p.getThuongHieu(), p.getGiaBan(), p.getSoLuong()
            });
        }
    }
    
    private void hienThongTinSanPham() {
        int row = tblDanhSachSP.getSelectedRow();
        if (row == -1) return;

        int id = (int) tblDanhSachSP.getValueAt(row, 0);

        String sql = "SELECT ID, TenSP, GiaBan, SoLuong, MaLoai, HinhAnh, MoTa FROM SanPham WHERE ID = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Gán dữ liệu sản phẩm
                    txtMaSP.setText(String.valueOf(rs.getInt("ID")));
                    txtMaSP.setEditable(false); 
                    txtTenSP.setText(rs.getString("TenSP"));
                    txtTenSP.setEditable(false); 
                    txtDonGia.setText(String.valueOf(rs.getDouble("GiaBan")));
                    txtDonGia.setEditable(false); 
                    txtLoai.setText(String.valueOf(rs.getInt("MaLoai")));
                    txtLoai.setEditable(false); 
                    txtMoTa.setText(rs.getString("MoTa"));
                    txtMoTa.setEditable(false); 

                    // Số lượng tồn kho
                    soLuongTonHienTai = rs.getInt("SoLuong");

                    // Người dùng nhập số lượng muốn mua
                    txtSoLuong.setText("1");

                    // Mã nhân viên lấy tự động
                    if (Auth.maNV != 0) { // nếu đã đăng nhập và có mã nhân viên
                        txtMaNV.setText(String.valueOf(Auth.maNV));
                    } else {
                        txtMaNV.setText("Chưa đăng nhập");
                    }

                    // Ảnh sản phẩm
                    String anh = tblDanhSachSP.getValueAt(row, 4).toString(); // cột chứa tên ảnh
                    txtAnh.setText(anh); // hiển thị tên ảnh trong ô text

                    // Nếu có label để hiển thị ảnh
                    ImageIcon icon = new ImageIcon(anh); // đường dẫn tới thư mục ảnh
                    Image img = icon.getImage().getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), Image.SCALE_SMOOTH);
                    lblAnh.setIcon(new ImageIcon(img));
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hiển thị thông tin sản phẩm: " + ex.getMessage());
            ex.printStackTrace();
        }
    }   
   
    // Hàm hiển thị ảnh sản phẩm từ đường dẫn/tên file
    private void setHinhAnh(String imagePath, javax.swing.JLabel label) {
        try {
            java.io.File file = new java.io.File(imagePath);

            if (!file.exists()) {
                file = new java.io.File("resources/images" + imagePath);
            }

            if (file.exists()) {
                java.awt.Image img = new javax.swing.ImageIcon(file.getAbsolutePath()).getImage();
                java.awt.Image scaled = img.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
                label.setIcon(new javax.swing.ImageIcon(scaled));
            } else {
                label.setIcon(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            label.setIcon(null);
        }
    }   

    private void themVaoGioHang() {
        try {
            String maSP = txtMaSP.getText();
            String tenSP = txtTenSP.getText();
            double donGia = Double.parseDouble(txtDonGia.getText());
            int soLuongMua = Integer.parseInt(txtSoLuong.getText());

            //Kiểm tra hợp lệ
            if (soLuongMua <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            if (soLuongMua > soLuongTonHienTai) {
                JOptionPane.showMessageDialog(this,
                    "Không đủ hàng trong kho. Chỉ còn " + soLuongTonHienTai + " sản phẩm.");
                return;
            }
            
            DecimalFormat dcf = new DecimalFormat("###,###");
            DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
            int foundRow = findRowByMaSP(maSP);

            if (foundRow != -1) {
                // sản phẩm đã có -> cập nhật số lượng và thành tiền
                int currentQty = 0;
                Object qtyObj = model.getValueAt(foundRow, 5); // cột 5 = Số lượng
                if (qtyObj != null) {
                    try { currentQty = Integer.parseInt(qtyObj.toString()); } catch (Exception ex) { currentQty = 0; }
                }
                int newQty = currentQty + soLuongMua;
                
                // kiểm tra tồn kho (nên so sánh với soLuongTonHienTai hoặc lấy kho thực tế)
                if (newQty > soLuongTonHienTai) {
                    JOptionPane.showMessageDialog(this,
                        "Không đủ hàng trong kho sau khi cộng. Chỉ còn " + soLuongTonHienTai + " sản phẩm.");
                    return;
                }

                double newThanhTien = donGia * newQty;
                model.setValueAt(newQty, foundRow, 4);
                model.setValueAt(newThanhTien, foundRow, 5);
                
            } else {    
                // thêm hàng mới
                double thanhTien = soLuongMua * donGia;
                model.addRow(new Object[] {
                    maSP, tenSP, txtLoai.getText().trim(), donGia, soLuongMua, thanhTien
                });
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm vào giỏ hàng: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private int findRowByMaSP(String maSP) {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        for (int r = 0; r < model.getRowCount(); r++) {
            Object o = model.getValueAt(r, 0); // giả sử cột 0 là Mã SP
            if (o != null && maSP.equals(o.toString())) {
                return r;
            }
        }
        return -1;
    }

//    private void setupTableRenderer() {
//        DecimalFormat df = new DecimalFormat("#,###");
//        DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
//            @Override
//            protected void setValue(Object value) {
//                if (value instanceof Number) {
//                    setText(df.format(value));
//                } else {
//                    setText(value != null ? value.toString() : "");
//                }
//            }
//        };
//        currencyRenderer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//
//        // Cột 3 = Đơn giá, Cột 5 = Thành tiền (chỉnh lại nếu vị trí khác)
////        tblGioHang.getColumnModel().getColumn(4).setCellRenderer(currencyRenderer);
//        tblGioHang.getColumnModel().getColumn(6).setCellRenderer(currencyRenderer);
//    }

    
    private void xoaSanPhamKhoiGioHang() {
        int row = tblGioHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm muốn xóa khỏi giỏ hàng!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm khỏi giỏ hàng!");
        }
    }   

//    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {                                           
//        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
//        int rowCount = model.getRowCount();
//
//        if (rowCount == 0) {
//            JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống!");
//            return;
//        }
//
//        int confirm = JOptionPane.showConfirmDialog(this, 
//            "Xác nhận mua hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//        if (confirm != JOptionPane.YES_OPTION) return;
//
//        double tongTien = 0;
//        for (int i = 0; i < rowCount; i++) {
//            tongTien += Double.parseDouble(model.getValueAt(i, 5).toString());
//        }
//
//        Connection conn = null;
//        PreparedStatement psHoaDon = null;
//        PreparedStatement psCT = null;
//        ResultSet rs = null;
//
//        try {
//            conn = DBConnect.getConnection();
//            conn.setAutoCommit(false); // Bắt đầu transaction
//            
//            // Thêm hóa đơn
//            String sqlHD = "INSERT INTO HoaDon (NgayLap, MaNV, TongTien) OUTPUT INSERTED.MaHD VALUES (GETDATE(), ?, ?)";
//            psHoaDon = conn.prepareStatement(sqlHD);
//            psHoaDon.setInt(1, Auth.maNV);
//            psHoaDon.setDouble(2, tongTien);
//            rs = psHoaDon.executeQuery();
//            
//            int maHD = 0;
//            if (rs.next()) {
//                maHD = rs.getInt("MaHD");
//            }
//
//            // Thêm chi tiết hóa đơn
//            String sqlCT = "INSERT INTO CTHoaDon (MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
//            psCT = conn.prepareStatement(sqlCT);
//
//            for (int i = 0; i < rowCount; i++) {
//                psCT.setInt(1, maHD);
//                psCT.setInt(2, Integer.parseInt(model.getValueAt(i, 0).toString())); // MaSP
//                psCT.setInt(3, Integer.parseInt(model.getValueAt(i, 4).toString())); // SoLuong
//                psCT.setDouble(4, Double.parseDouble(model.getValueAt(i, 3).toString())); // DonGia
//                psCT.setDouble(5, Double.parseDouble(model.getValueAt(i, 5).toString())); // ThanhTien
//                psCT.addBatch();
//            }
//            
//            psCT.executeBatch();
//            conn.commit();
//            
//            // Hiển thị hóa đơn
//            DecimalFormat df = new DecimalFormat("#,###");
//            StringBuilder sb = new StringBuilder("===== HÓA ĐƠN MUA HÀNG =====\n\n");
//            for (int i = 0; i < rowCount; i++) {
//                sb.append(String.format("%s  SL:%s  Thành tiền: %s đ\n",
//                        model.getValueAt(i, 1), // tên sản phẩm
//                        model.getValueAt(i, 4),
//                        df.format(Double.parseDouble(model.getValueAt(i, 5).toString()))
//                ));
//            }
//            sb.append("\nTỔNG TIỀN: ").append(df.format(tongTien)).append(" đ");
//            
////            JTextArea textArea = new JTextArea(sb.toString());
////            textArea.setEditable(false);
////            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), 
////                "Hóa đơn chi tiết", JOptionPane.INFORMATION_MESSAGE);
//
////            XuatHoaDonGUI xuatPanel = new XuatHoaDonGUI(listSP);
////            XuatHoaDonGUI xuatPanel = new XuatHoaDonGUI();
////            // Hiển thị trong JFrame mới
////            JFrame frame = new JFrame("Hóa đơn chi tiết");
////            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////            frame.setContentPane(xuatPanel);
////            frame.pack();
////            frame.setLocationRelativeTo(this); // căn giữa form hiện tại
////            frame.setVisible(true);
//            
//            // Xóa khỏi giỏ hàng
//            model.setRowCount(0);
//
//            JOptionPane.showMessageDialog(this, "Mua hàng thành công!");
//            
//        } catch (Exception ex) {
//            try { if (conn != null) conn.rollback(); } catch (Exception e1) {}
//            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage());
//            ex.printStackTrace();
//        } finally {
//            try { if (rs != null) rs.close(); } catch (Exception e) {}
//            try { if (psHoaDon != null) psHoaDon.close(); } catch (Exception e) {}
//            try { if (psCT != null) psCT.close(); } catch (Exception e) {}
//            try { if (conn != null) conn.close(); } catch (Exception e) {}
//        }
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDanhSachSP = new javax.swing.JPanel();
        lblDanhSachSP = new javax.swing.JLabel();
        lblTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        scrDanhSachSP = new javax.swing.JScrollPane();
        tblDanhSachSP = new javax.swing.JTable();
        btnTim = new javax.swing.JButton();
        pnlGioHang = new javax.swing.JPanel();
        lblGioHang = new javax.swing.JLabel();
        scrGioHang = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        pnlThongTinSP = new javax.swing.JPanel();
        lblThongTinSP = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        lblTenSP = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        lblDonGia = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        lblSoLuong = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        lblLoai = new javax.swing.JLabel();
        txtLoai = new javax.swing.JTextField();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        lblAnh = new javax.swing.JLabel();
        lblMoTa = new javax.swing.JLabel();
        txtAnh = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();

        pnlDanhSachSP.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));

        lblDanhSachSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDanhSachSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDanhSachSP.setText("Danh sách sản phẩm");

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTimKiem.setText("Tìm kiếm");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        scrDanhSachSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tblDanhSachSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Loại", "Đơn giá", "Mô tả"
            }
        ));
        tblDanhSachSP.setGridColor(new java.awt.Color(51, 51, 51));
        tblDanhSachSP.setRowHeight(23);
        tblDanhSachSP.setShowGrid(true);
        scrDanhSachSP.setViewportView(tblDanhSachSP);

        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/undo.png"))); // NOI18N
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDanhSachSPLayout = new javax.swing.GroupLayout(pnlDanhSachSP);
        pnlDanhSachSP.setLayout(pnlDanhSachSPLayout);
        pnlDanhSachSPLayout.setHorizontalGroup(
            pnlDanhSachSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDanhSachSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrDanhSachSP, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachSPLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTim)
                        .addGap(104, 104, 104))
                    .addComponent(lblDanhSachSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDanhSachSPLayout.setVerticalGroup(
            pnlDanhSachSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDanhSachSP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDanhSachSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDanhSachSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTimKiem)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrDanhSachSP, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlGioHang.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));

        lblGioHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGioHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGioHang.setText("Giỏ hàng");

        tblGioHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Loại", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ));
        tblGioHang.setGridColor(new java.awt.Color(51, 51, 51));
        tblGioHang.setRowHeight(23);
        tblGioHang.setShowGrid(true);
        scrGioHang.setViewportView(tblGioHang);

        javax.swing.GroupLayout pnlGioHangLayout = new javax.swing.GroupLayout(pnlGioHang);
        pnlGioHang.setLayout(pnlGioHangLayout);
        pnlGioHangLayout.setHorizontalGroup(
            pnlGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addComponent(lblGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGioHangLayout.setVerticalGroup(
            pnlGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGioHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlThongTinSP.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));

        lblThongTinSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThongTinSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThongTinSP.setText("Thông tin sản phẩm");

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaSP.setText("Mã sản phẩm");

        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });

        lblTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenSP.setText("Tên sản phẩm");

        txtTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSPActionPerformed(evt);
            }
        });

        lblDonGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDonGia.setText("Đơn giá");

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSoLuong.setText("Số lượng");

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        lblLoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLoai.setText("Loại");

        txtLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoaiActionPerformed(evt);
            }
        });

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaNV.setText("Mã nhân viên");

        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        lblAnh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAnh.setText("Ảnh");

        lblMoTa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMoTa.setText("Mô tả");

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThem.setText("Thêm vào giỏ");

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/xoa.png"))); // NOI18N
        btnXoa.setText("Xóa khỏi giỏ");

        btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/xacnhan.png"))); // NOI18N
        btnXacNhan.setText("Xác nhận");

        javax.swing.GroupLayout pnlThongTinSPLayout = new javax.swing.GroupLayout(pnlThongTinSP);
        pnlThongTinSP.setLayout(pnlThongTinSPLayout);
        pnlThongTinSPLayout.setHorizontalGroup(
            pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinSPLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinSPLayout.createSequentialGroup()
                        .addComponent(lblMaSP)
                        .addGap(21, 21, 21)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinSPLayout.createSequentialGroup()
                        .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLoai)
                            .addComponent(lblSoLuong)
                            .addComponent(lblTenSP)
                            .addComponent(lblDonGia)
                            .addComponent(lblMaNV))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDonGia)
                                    .addComponent(txtTenSP)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinSPLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(btnThem)
                            .addGap(43, 43, 43)
                            .addComponent(btnXoa))
                        .addGroup(pnlThongTinSPLayout.createSequentialGroup()
                            .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblAnh)
                                .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(35, 35, 35)
                            .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblMoTa)
                                .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(33, 33, 33))
            .addGroup(pnlThongTinSPLayout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(btnXacNhan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlThongTinSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThongTinSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlThongTinSPLayout.setVerticalGroup(
            pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThongTinSP)
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaSP)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenSP)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDonGia)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSoLuong)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLoai)
                    .addComponent(txtLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNV)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnh)
                    .addComponent(lblMoTa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXacNhan)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlDanhSachSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(btnLamMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(pnlThongTinSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDanhSachSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLamMoi)
                        .addGap(18, 18, 18)
                        .addComponent(pnlGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlThongTinSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoaiActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblDanhSachSP;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblGioHang;
    private javax.swing.JLabel lblLoai;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblThongTinSP;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnlDanhSachSP;
    private javax.swing.JPanel pnlGioHang;
    private javax.swing.JPanel pnlThongTinSP;
    private javax.swing.JScrollPane scrDanhSachSP;
    private javax.swing.JScrollPane scrGioHang;
    private javax.swing.JTable tblDanhSachSP;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTextField txtAnh;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtLoai;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
