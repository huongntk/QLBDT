package GUI;

import BUS.ThongKeBUS;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar; 
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PnThongKe extends javax.swing.JPanel {

    private ThongKeBUS thongKeBUS;
    private DefaultTableModel modelDoanhThu;
    private DefaultTableModel modelSanPham;
    private Locale localeVN = new Locale("vi", "VN"); 
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);


    public PnThongKe() {
        initComponents();

        thongKeBUS = new ThongKeBUS();
        modelDoanhThu = (DefaultTableModel) tblDoanhThu.getModel();
        modelSanPham = (DefaultTableModel) tblSanPham.getModel();
        
        setupTableHeaders();
        setDefaults();
        addEvents();
    }
    
    private void setupTableHeaders() {
        javax.swing.table.JTableHeader headerDT = tblDoanhThu.getTableHeader();
        ((javax.swing.table.DefaultTableCellRenderer) headerDT.getDefaultRenderer())
                .setHorizontalAlignment(javax.swing.JLabel.CENTER);
        headerDT.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        javax.swing.table.JTableHeader headerSP = tblSanPham.getTableHeader();
        ((javax.swing.table.DefaultTableCellRenderer) headerSP.getDefaultRenderer())
                .setHorizontalAlignment(javax.swing.JLabel.CENTER);
        headerSP.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    }
    
    private void setDefaults() {
        Calendar cal = Calendar.getInstance();
        
        dateChooserStart.setDate(cal.getTime());
        dateChooserEnd.setDate(cal.getTime());
        
        cboThang.setSelectedIndex(cal.get(Calendar.MONTH)); 
        yearChooser.setYear(cal.get(Calendar.YEAR));
    }
    
    private void addEvents() {
        
        btnThongKeDoanhThu.addActionListener(e -> {
            thucHienThongKeDoanhThu();
        });
        
        btnThongKeSanPham.addActionListener(e -> {
            thucHienThongKeSanPham();
        });
    }

    private void thucHienThongKeDoanhThu() {
        Date ngayBD = dateChooserStart.getDate();
        Date ngayKT = dateChooserEnd.getDate();

        if (ngayBD == null || ngayKT == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (ngayKT.before(ngayBD)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trước ngày bắt đầu.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<Object[]> ketQua = thongKeBUS.thongKeDoanhThu(ngayBD, ngayKT);
        
        modelDoanhThu.setRowCount(0); 
        for (Object[] row : ketQua) {
            row[2] = currencyFormatter.format((int) row[2]);
            modelDoanhThu.addRow(row);
        }
        
        int tongDoanhThu = thongKeBUS.getTongDoanhThu(ngayBD, ngayKT);
        txtTongDoanhThu.setText(currencyFormatter.format(tongDoanhThu));
    }
    
    private void thucHienThongKeSanPham() {
        int thang = Integer.parseInt(cboThang.getSelectedItem().toString());
        int nam = yearChooser.getYear();

        ArrayList<Object[]> ketQua = thongKeBUS.thongKeSanPham(thang, nam);
        
        modelSanPham.setRowCount(0); 
        if (ketQua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu cho tháng/năm đã chọn.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        
        for (Object[] row : ketQua) {
            row[3] = currencyFormatter.format((int) row[3]);
            modelSanPham.addRow(row);
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        tabbedPaneThongKe = new javax.swing.JTabbedPane();
        pnlDoanhThu = new javax.swing.JPanel();
        pnlBoLocDoanhThu = new javax.swing.JPanel();
        lblNgayBatDau = new javax.swing.JLabel();
        dateChooserStart = new com.toedter.calendar.JDateChooser();
        lblNgayKetThuc = new javax.swing.JLabel();
        dateChooserEnd = new com.toedter.calendar.JDateChooser();
        btnThongKeDoanhThu = new javax.swing.JButton();
        pnlBangDoanhThu = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDoanhThu = new javax.swing.JTable();
        lblTongDoanhThu = new javax.swing.JLabel();
        txtTongDoanhThu = new javax.swing.JTextField();
        pnlSanPham = new javax.swing.JPanel();
        pnlBoLocSanPham = new javax.swing.JPanel();
        lblThang = new javax.swing.JLabel();
        cboThang = new javax.swing.JComboBox<>();
        lblNam = new javax.swing.JLabel();
        yearChooser = new com.toedter.calendar.JYearChooser();
        btnThongKeSanPham = new javax.swing.JButton();
        pnlBangSanPham = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        tabbedPaneThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        pnlDoanhThu.setLayout(new java.awt.BorderLayout());

        pnlBoLocDoanhThu.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Bộ lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblNgayBatDau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNgayBatDau.setText("Từ ngày:");

        dateChooserStart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNgayKetThuc.setText("Đến ngày:");

        dateChooserEnd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnThongKeDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThongKeDoanhThu.setText("Thống kê");

        javax.swing.GroupLayout pnlBoLocDoanhThuLayout = new javax.swing.GroupLayout(pnlBoLocDoanhThu);
        pnlBoLocDoanhThu.setLayout(pnlBoLocDoanhThuLayout);
        pnlBoLocDoanhThuLayout.setHorizontalGroup(
            pnlBoLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoLocDoanhThuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblNgayBatDau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblNgayKetThuc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooserEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnThongKeDoanhThu)
                .addContainerGap(474, Short.MAX_VALUE))
        );
        pnlBoLocDoanhThuLayout.setVerticalGroup(
            pnlBoLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoLocDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBoLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThongKeDoanhThu)
                    .addGroup(pnlBoLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateChooserEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNgayKetThuc, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNgayBatDau, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlDoanhThu.add(pnlBoLocDoanhThu, java.awt.BorderLayout.NORTH);

        tblDoanhThu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thời gian", "Số hóa đơn đã bán", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoanhThu.setGridColor(new java.awt.Color(51, 51, 51));
        tblDoanhThu.setRowHeight(23);
        tblDoanhThu.setShowGrid(true);
        tblDoanhThu.setShowHorizontalLines(true);
        tblDoanhThu.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tblDoanhThu);

        lblTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTongDoanhThu.setText("Tổng doanh thu:");

        txtTongDoanhThu.setEditable(false);
        txtTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtTongDoanhThu.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout pnlBangDoanhThuLayout = new javax.swing.GroupLayout(pnlBangDoanhThu);
        pnlBangDoanhThu.setLayout(pnlBangDoanhThuLayout);
        pnlBangDoanhThuLayout.setHorizontalGroup(
            pnlBangDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBangDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBangDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBangDoanhThuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTongDoanhThu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlBangDoanhThuLayout.setVerticalGroup(
            pnlBangDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBangDoanhThuLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlBangDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongDoanhThu)
                    .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlDoanhThu.add(pnlBangDoanhThu, java.awt.BorderLayout.CENTER);

        tabbedPaneThongKe.addTab("Doanh thu", pnlDoanhThu);

        pnlSanPham.setLayout(new java.awt.BorderLayout());

        pnlBoLocSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Bộ lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblThang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblThang.setText("Tháng:");

        cboThang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        lblNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNam.setText("Năm:");

        btnThongKeSanPham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThongKeSanPham.setText("Thống kê");

        javax.swing.GroupLayout pnlBoLocSanPhamLayout = new javax.swing.GroupLayout(pnlBoLocSanPham);
        pnlBoLocSanPham.setLayout(pnlBoLocSanPhamLayout);
        pnlBoLocSanPhamLayout.setHorizontalGroup(
            pnlBoLocSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoLocSanPhamLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblThang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnThongKeSanPham)
                .addContainerGap(576, Short.MAX_VALUE))
        );
        pnlBoLocSanPhamLayout.setVerticalGroup(
            pnlBoLocSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoLocSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBoLocSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThongKeSanPham)
                    .addComponent(yearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlBoLocSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblThang)
                        .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNam)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlSanPham.add(pnlBoLocSanPham, java.awt.BorderLayout.NORTH);

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng đã bán", "Tổng giá trị"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setGridColor(new java.awt.Color(51, 51, 51));
        tblSanPham.setRowHeight(23);
        tblSanPham.setShowGrid(true);
        tblSanPham.setShowHorizontalLines(true);
        tblSanPham.setShowVerticalLines(true);
        jScrollPane2.setViewportView(tblSanPham);

        javax.swing.GroupLayout pnlBangSanPhamLayout = new javax.swing.GroupLayout(pnlBangSanPham);
        pnlBangSanPham.setLayout(pnlBangSanPhamLayout);
        pnlBangSanPhamLayout.setHorizontalGroup(
            pnlBangSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBangSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBangSanPhamLayout.setVerticalGroup(
            pnlBangSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBangSanPhamLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlSanPham.add(pnlBangSanPham, java.awt.BorderLayout.CENTER);

        tabbedPaneThongKe.addTab("Sản phẩm bán chạy", pnlSanPham);

        add(tabbedPaneThongKe, java.awt.BorderLayout.CENTER);
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnThongKeDoanhThu;
    private javax.swing.JButton btnThongKeSanPham;
    private javax.swing.JComboBox<String> cboThang;
    private com.toedter.calendar.JDateChooser dateChooserEnd;
    private com.toedter.calendar.JDateChooser dateChooserStart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNam;
    private javax.swing.JLabel lblNgayBatDau;
    private javax.swing.JLabel lblNgayKetThuc;
    private javax.swing.JLabel lblThang;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JPanel pnlBangDoanhThu;
    private javax.swing.JPanel pnlBangSanPham;
    private javax.swing.JPanel pnlBoLocDoanhThu;
    private javax.swing.JPanel pnlBoLocSanPham;
    private javax.swing.JPanel pnlDoanhThu;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JTabbedPane tabbedPaneThongKe;
    private javax.swing.JTable tblDoanhThu;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtTongDoanhThu;
    private com.toedter.calendar.JYearChooser yearChooser;
    // End of variables declaration                   
}