/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import DTO.Product;
import BUS.CTSanPhamBUS;
import BUS.SanPhamBUS;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import DTO.ProductDetail;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/**
 *
 * @author ltd96
 */
public class PnSanPham extends javax.swing.JPanel {
        private ArrayList<Product> list;

        /**
         * Creates new form PnSanPham
         */
        private void loadDataToTable() {
                SanPhamBUS bus = new SanPhamBUS();
                list = bus.getSanPham();
                String[] columnNames = { "ID", "Tên sản phẩm", "Thương hiệu", "Xuất xứ", "Mã loại", "Giới tính",
                                "Giá bán",
                                "Số lượng", "Hình ảnh", "Mô tả", "Mã NCC" };

                // **QUAN TRỌNG: Ghi đè getColumnClass ở đây**
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                                // Chỉ định cột 8 (Hình ảnh) là kiểu ImageIcon.class
                                if (columnIndex == 8) {
                                        return ImageIcon.class;
                                }
                                // Giữ nguyên các cột khác
                                return super.getColumnClass(columnIndex);
                        }
                };

                for (Product p : list) {
                        // Giả sử bạn đang dùng hàm loadAndScaleIcon tôi gửi trước đó
                        ImageIcon scaledIcon = loadAndScaleIcon(p.getHinhAnh(), 40, 40);

                        Object[] rowData = { p.getID(), p.getTenSP(), p.getThuongHieu(), p.getXuatXu(), p.getMaLoai(),
                                        p.getGioiTinh(), p.getGiaBan(), p.getSoLuong(), scaledIcon, // Thêm đối tượng
                                                                                                    // ImageIcon
                                        p.getMoTa(),
                                        p.getMaNCC() };
                        model.addRow(rowData);
                }
                jTable1.setModel(model);
                jTable1.setRowHeight(40);
        }

        private void loadDetailtoTextField() {
                jTable1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent evt) {
                                int row = jTable1.getSelectedRow();
                                // Kiểm tra nếu không có hàng nào được chọn
                                if (row == -1) {
                                        return;
                                }
                                int column = jTable1.getColumnModel().getColumnIndex("ID");
                                int selectedID = Integer.parseInt(jTable1.getValueAt(row, column).toString());
                                CTSanPhamBUS bus = new CTSanPhamBUS();
                                ProductDetail detail = bus.getDetail(selectedID);
                                // Kiểm tra nếu không có chi tiết
                                if (detail == null) {
                                        // Xóa trống các trường
                                        jTextField2.setText("");
                                        jTextField3.setText("");
                                        jTextField4.setText("");
                                        jTextField5.setText("");
                                        jTextField6.setText("");
                                        jTextField7.setText("");
                                        jTextField8.setText("");
                                        jTextField9.setText("");
                                        jTextField10.setText("");
                                        jTextField11.setText("");
                                        jTextField12.setText("");
                                        jTextField13.setText("");
                                        jTextField14.setText("");
                                        jTextField15.setText("");
                                        return;
                                }
                                jTextField2.setText(detail.getDuongKinhMat());
                                jTextField3.setText(detail.getDoDayMat());
                                jTextField4.setText(detail.getMauMatSo());
                                jTextField5.setText(detail.getChatLieuVo());
                                jTextField6.setText(detail.getChatLieuDay());
                                jTextField7.setText(detail.getKinh());
                                jTextField8.setText(detail.getKieuMat());
                                jTextField9.setText(detail.getBoMay());
                                jTextField10.setText(detail.getNangLuongCo());
                                jTextField11.setText(detail.getThoiGianTruCoc());
                                jTextField12.setText(detail.getDoChiuNuoc());
                                jTextField13.setText(detail.getChucNangKhac());
                                jTextField14.setText(String.valueOf(detail.getTrongLuong()));
                                jTextField15.setText(detail.getBaoHanh());
                        }

                });

        }

        public PnSanPham() {
                initComponents();
                setupCustomLayout();
                loadDataToTable();
                loadDetailtoTextField();
        }

        /**
         * /**
         * =========================================================================
         * HÀM SETUP LAYOUT MỚI (ĐÃ SỬA LỖI NÉN JTEXTFIELD)
         * =========================================================================
         */
        private void setupCustomLayout() {
                // 1. Gỡ bỏ layout cũ (GroupLayout)
                this.removeAll();

                // 2. Thiết lập BorderLayout làm layout mới cho panel chính (this)
                this.setLayout(new java.awt.BorderLayout(5, 5)); // 5, 5 là khoảng cách

                // 3. KHỐI EAST (Chi tiết sản phẩm) - Logic này đã đúng
                JPanel eastPanel = new JPanel(new java.awt.BorderLayout(5, 5));
                eastPanel.add(jPanel3, java.awt.BorderLayout.CENTER);
                JPanel detailButtonPanel = new JPanel(); // Dùng FlowLayout mặc định
                detailButtonPanel.add(jButton6);
                detailButtonPanel.add(jButton7);
                eastPanel.add(detailButtonPanel, java.awt.BorderLayout.SOUTH);

                // 4. KHỐI CENTER (Bảng và Tìm kiếm)
                JPanel centerPanel = new JPanel(new java.awt.BorderLayout(5, 5));

                // 4b. Tạo một panel mới cho thanh tìm kiếm/lọc
                JPanel searchPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT)); // Căn trái
                searchPanel.add(new JLabel("Tìm kiếm:"));

                // =================================================================
                // **THÊM DÒNG NÀY ĐỂ SỬA LỖI BỊ NÉN**
                // Đặt lại kích thước mong muốn, giống như GroupLayout đã làm (181)
                jTextField1.setPreferredSize(new java.awt.Dimension(181, jTextField1.getPreferredSize().height));
                // =================================================================

                searchPanel.add(jTextField1);
                searchPanel.add(jButton5);
                searchPanel.add(new JLabel("  Lọc theo loại:"));
                searchPanel.add(jComboBox1);

                // 4c. Đặt thanh tìm kiếm/lọc lên phía BẮC của centerPanel
                centerPanel.add(searchPanel, java.awt.BorderLayout.NORTH);

                // 4d. Đặt BẢNG (nằm trong JScrollPane) vào TRUNG TÂM của centerPanel
                centerPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                // 5. Thêm 3 khối chính vào panel (this)
                this.add(jPanel1, java.awt.BorderLayout.NORTH); // Thanh công cụ (Thêm, Sửa, Xóa)
                this.add(centerPanel, java.awt.BorderLayout.CENTER); // Bảng và Tìm kiếm (Đã sửa)
                this.add(eastPanel, java.awt.BorderLayout.EAST); // Chi tiết sản phẩm
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
                openInsertDialog();

        }// <editor-fold defaultstate="collapsed" desc="Generated Code">

        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();
                jButton3 = new javax.swing.JButton();
                jButton4 = new javax.swing.JButton();
                jPanel2 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                jTable1 = new javax.swing.JTable();
                jLabel1 = new javax.swing.JLabel();
                jTextField1 = new javax.swing.JTextField();
                jButton5 = new javax.swing.JButton();
                jComboBox1 = new javax.swing.JComboBox<>();
                jPanel3 = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jTextField2 = new javax.swing.JTextField();
                jTextField3 = new javax.swing.JTextField();
                jLabel5 = new javax.swing.JLabel();
                jTextField4 = new javax.swing.JTextField();
                jLabel6 = new javax.swing.JLabel();
                jTextField5 = new javax.swing.JTextField();
                jLabel7 = new javax.swing.JLabel();
                jTextField6 = new javax.swing.JTextField();
                jLabel8 = new javax.swing.JLabel();
                jTextField7 = new javax.swing.JTextField();
                jLabel9 = new javax.swing.JLabel();
                jTextField8 = new javax.swing.JTextField();
                jLabel10 = new javax.swing.JLabel();
                jTextField9 = new javax.swing.JTextField();
                jLabel11 = new javax.swing.JLabel();
                jTextField10 = new javax.swing.JTextField();
                jLabel12 = new javax.swing.JLabel();
                jTextField11 = new javax.swing.JTextField();
                jLabel13 = new javax.swing.JLabel();
                jTextField12 = new javax.swing.JTextField();
                jLabel14 = new javax.swing.JLabel();
                jTextField13 = new javax.swing.JTextField();
                jLabel15 = new javax.swing.JLabel();
                jTextField14 = new javax.swing.JTextField();
                jLabel16 = new javax.swing.JLabel();
                jTextField15 = new javax.swing.JTextField();
                jButton6 = new javax.swing.JButton();
                jButton7 = new javax.swing.JButton();

                setPreferredSize(new java.awt.Dimension(1280, 600));

                jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
                jButton1.setText("Thêm");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sua.png"))); // NOI18N
                jButton2.setText("Sửa");
                jButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton2ActionPerformed(evt);
                        }
                });

                jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/xoa.png"))); // NOI18N
                jButton3.setText("Xoá");
                jButton3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton3ActionPerformed(evt);
                        }
                });
                jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png")));
                jButton4.setText("Làm mới");
                jButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton4ActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addComponent(jButton1)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton2)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton3)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton4)
                                                                .addContainerGap(148, Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jButton1)
                                                                                .addComponent(jButton2)
                                                                                .addComponent(jButton3)
                                                                                .addComponent(jButton4))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

                jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {

                                }));
                jScrollPane1.setViewportView(jTable1);

                jLabel1.setText("Danh sách sản phẩm");

                jTextField1.setText("");

                jButton5.setText("Tìm");
                jButton5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton5ActionPerformed(evt);
                        }
                });
                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Loại", "Hand-Winding", "Automatic", "Đồng hồ cơ lai" }));
                jComboBox1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jComboBox1ActionPerformed(evt);
                        }

                });
                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jTextField1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                181,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jButton5)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(jComboBox1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                126,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(129, 129, 129))
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jLabel1,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                793,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(jScrollPane1,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                935,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addContainerGap(7,
                                                                                                                Short.MAX_VALUE)))));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1)
                                                                .addGap(26, 26, 26)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jTextField1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jButton5)
                                                                                .addComponent(jComboBox1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                455,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

                jLabel2.setText("Chi tiết sản phẩm");

                jLabel3.setText("Đường kính");

                jLabel4.setText("Độ dày");

                jTextField2.setEditable(false);

                jTextField3.setEditable(false);

                jLabel5.setText("Màu sắc");

                jTextField4.setEditable(false);

                jLabel6.setText("Vỏ");

                jTextField5.setEditable(false);

                jLabel7.setText("Dây");

                jTextField6.setEditable(false);

                jLabel8.setText("Kính");

                jTextField7.setEditable(false);

                jLabel9.setText("KIểu dáng");

                jTextField8.setEditable(false);

                jLabel10.setText("Bộ máy");

                jTextField9.setEditable(false);

                jLabel11.setText("Năng lượng cơ");

                jTextField10.setEditable(false);

                jLabel12.setText("Thời gian");

                jTextField11.setEditable(false);

                jLabel13.setText("Chống nước");

                jTextField12.setEditable(false);

                jLabel14.setText("Chức năng khác");

                jTextField13.setEditable(false);

                jLabel15.setText("Trọng lượng");

                jTextField14.setEditable(false);

                jLabel16.setText("Bảo hành");

                jTextField15.setEditable(false);

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel3Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel3Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                false)
                                                                                                                .addComponent(jLabel16,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jLabel14,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jLabel13,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jLabel11,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                false)
                                                                                                                                                .addComponent(jLabel9,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jLabel8,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jLabel7,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                false)
                                                                                                                                                                                .addComponent(jLabel5,
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addComponent(jLabel3,
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                80,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addComponent(jLabel4,
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                                .addComponent(jLabel6)))
                                                                                                                                .addComponent(jLabel10))
                                                                                                                .addComponent(jLabel12,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jLabel15,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel3Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(jTextField2)
                                                                                                                .addComponent(jTextField3)
                                                                                                                .addComponent(jTextField5)
                                                                                                                .addComponent(jTextField4)
                                                                                                                .addComponent(jTextField6)
                                                                                                                .addComponent(jTextField7)
                                                                                                                .addComponent(jTextField8)
                                                                                                                .addComponent(jTextField9)
                                                                                                                .addComponent(jTextField10,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                167,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jTextField11)
                                                                                                                .addComponent(jTextField12)
                                                                                                                .addComponent(jTextField13)
                                                                                                                .addComponent(jTextField14)
                                                                                                                .addComponent(jTextField15))
                                                                                                .addGap(0, 4, Short.MAX_VALUE)))
                                                                .addContainerGap()));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel2)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(jTextField2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jTextField3,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel4))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(jTextField4,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel6)
                                                                                .addComponent(jTextField5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel7)
                                                                                .addComponent(jTextField6,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel8)
                                                                                .addComponent(jTextField7,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(jTextField8,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel10)
                                                                                .addComponent(jTextField9,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel11)
                                                                                .addComponent(jTextField10,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel12)
                                                                                .addComponent(jTextField11,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel13)
                                                                                .addComponent(jTextField12,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel14)
                                                                                .addComponent(jTextField13,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel15)
                                                                                .addComponent(jTextField14,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel16)
                                                                                .addComponent(jTextField15,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(20, Short.MAX_VALUE)));

                jButton6.setText("Sửa chi tiết ");
                jButton6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton6ActionPerformed(evt);
                        }
                });

                jButton7.setText("Lưu");
                jButton7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton7ActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(jPanel2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(jPanel3,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(jButton6)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(jButton7)))))
                                                                .addGap(219, 219, 219)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(jPanel3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jButton6)
                                                                                                                .addComponent(jButton7))))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
        }// </editor-fold>

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để sửa!");
                        return;
                }
                int id = Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString());
                SanPhamBUS bus = new SanPhamBUS();
                ArrayList<Product> products = bus.getSanPhamById(id);
                if (products.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với ID: " + id);
                        return;
                }
                Product selectedProduct = products.get(0);
                openEditDialog(selectedProduct);

        }// GEN-LAST:event_jButton2ActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
                int row = jTable1.getSelectedRow();
                if (row == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!");
                        return;
                }
                int column = jTable1.getColumnModel().getColumnIndex("ID");
                int selectedID = Integer.parseInt(jTable1.getValueAt(row, column).toString());
                SanPhamBUS bus = new SanPhamBUS();
                bus.deleteSanPham(selectedID);
                loadDataToTable();
                loadDetailtoTextField();
        }// GEN-LAST:event_jButton3ActionPerformed

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
                loadDataToTable();
        }// GEN-LAST:event_jButton4ActionPerformed

        private void jButton5ActionPerformed(ActionEvent evt) {
                String searchText = jTextField1.getText();
                if (searchText.trim().isEmpty()) {
                        loadDataToTable();
                        return;
                }
                ArrayList<Product> listBySearch = new ArrayList<>();
                SanPhamBUS bus = new SanPhamBUS();
                listBySearch = bus.getSanPhamByName(searchText);
                String[] columnNames = { "ID", "Tên sản phẩm", "Thương hiệu", "Xuất xứ", "Mã loại", "Giới tính",
                                "Giá bán",
                                "Số lượng", "Hình ảnh", "Mô tả", "Mã NCC" };

                // **QUAN TRỌNG: Ghi đè getColumnClass ở đây**
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                                if (columnIndex == 8) {
                                        return ImageIcon.class;
                                }
                                return super.getColumnClass(columnIndex);
                        }
                };

                for (Product p : listBySearch) {
                        ImageIcon scaledIcon = loadAndScaleIcon(p.getHinhAnh(), 40, 40);
                        Object[] rowData = { p.getID(), p.getTenSP(), p.getThuongHieu(), p.getXuatXu(), p.getMaLoai(),
                                        p.getGioiTinh(), p.getGiaBan(), p.getSoLuong(), scaledIcon, p.getMoTa(),
                                        p.getMaNCC() };
                        model.addRow(rowData);
                }
                jTable1.setModel(model);
                jTable1.setRowHeight(40);
        }

        private void jComboBox1ActionPerformed(ActionEvent evt) {
                String selected = (String) jComboBox1.getSelectedItem();
                ArrayList<Product> listByCate = new ArrayList<>();
                SanPhamBUS bus = new SanPhamBUS();
                int maLoaiTmp = -1;
                switch (selected) {
                        case "Hand-Winding" -> maLoaiTmp = 1;
                        case "Automatic" -> maLoaiTmp = 2;
                        case "Đồng hồ cơ lai" -> maLoaiTmp = 3;
                        case "Loại" -> {
                                loadDataToTable();
                                return;
                        }
                        default -> {
                                JOptionPane.showMessageDialog(this, "Lỗi!");
                                return;
                        }
                }
                listByCate = bus.getSanPhamByCate(maLoaiTmp);
                String[] columnNames = { "ID", "Tên sản phẩm", "Thương hiệu", "Xuất xứ", "Mã loại", "Giới tính",
                                "Giá bán",
                                "Số lượng", "Hình ảnh", "Mô tả", "Mã NCC" };

                // **QUAN TRỌNG: Ghi đè getColumnClass ở đây**
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                                if (columnIndex == 8) {
                                        return ImageIcon.class;
                                }
                                return super.getColumnClass(columnIndex);
                        }
                };

                for (Product p : listByCate) {
                        ImageIcon scaledIcon = loadAndScaleIcon(p.getHinhAnh(), 40, 40);
                        Object[] rowData = { p.getID(), p.getTenSP(), p.getThuongHieu(), p.getXuatXu(), p.getMaLoai(),
                                        p.getGioiTinh(), p.getGiaBan(), p.getSoLuong(), scaledIcon, p.getMoTa(),
                                        p.getMaNCC() };
                        model.addRow(rowData);
                }
                jTable1.setModel(model);
                jTable1.setRowHeight(40);
        }

        private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để lưu chi tiết!");
                        return;
                }
                int id = Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString());
                ProductDetail detail = new ProductDetail();
                detail.setID(id);
                detail.setDuongKinhMat(jTextField2.getText());
                detail.setDoDayMat(jTextField3.getText());
                detail.setMauMatSo(jTextField4.getText());
                detail.setChatLieuVo(jTextField5.getText());
                detail.setChatLieuDay(jTextField6.getText());
                detail.setKinh(jTextField7.getText());
                detail.setKieuMat(jTextField8.getText());
                detail.setBoMay(jTextField9.getText());
                detail.setNangLuongCo(jTextField10.getText());
                detail.setThoiGianTruCoc(jTextField11.getText());
                detail.setDoChiuNuoc(jTextField12.getText());
                detail.setChucNangKhac(jTextField13.getText());
                detail.setTrongLuong(Float.valueOf(jTextField14.getText()));
                detail.setBaoHanh(jTextField15.getText());
                CTSanPhamBUS bus = new CTSanPhamBUS();
                boolean result = bus.updateCTSanPham(detail);
                if (result) {
                        JOptionPane.showMessageDialog(this, "Cập nhật chi tiết sản phẩm thành công!");
                } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
                setEditable(false);
        }// GEN-LAST:event_jButton7ActionPerformed

        private void setEditable(boolean editable) {
                jTextField2.setEditable(editable);
                jTextField3.setEditable(editable);
                jTextField4.setEditable(editable);
                jTextField5.setEditable(editable);
                jTextField6.setEditable(editable);
                jTextField7.setEditable(editable);
                jTextField8.setEditable(editable);
                jTextField9.setEditable(editable);
                jTextField10.setEditable(editable);
                jTextField11.setEditable(editable);
                jTextField12.setEditable(editable);
                jTextField13.setEditable(editable);
                jTextField14.setEditable(editable);
                jTextField15.setEditable(editable);
        }

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để sửa chi tiết!");
                        return;
                }
                setEditable(true);
        }// GEN-LAST:event_jButton6ActionPerformed

        @SuppressWarnings("empty-statement")

        // Variables declaration - do not modify
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
        private javax.swing.JButton jButton5;
        private javax.swing.JButton jButton6;
        private javax.swing.JButton jButton7;
        private javax.swing.JComboBox<String> jComboBox1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel16;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable jTable1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField10;
        private javax.swing.JTextField jTextField11;
        private javax.swing.JTextField jTextField12;
        private javax.swing.JTextField jTextField13;
        private javax.swing.JTextField jTextField14;
        private javax.swing.JTextField jTextField15;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTextField jTextField3;
        private javax.swing.JTextField jTextField4;
        private javax.swing.JTextField jTextField5;
        private javax.swing.JTextField jTextField6;
        private javax.swing.JTextField jTextField7;
        private javax.swing.JTextField jTextField8;
        private javax.swing.JTextField jTextField9;
        // End of variables declaration

        // edit pn

        private void openEditDialog(Product p) {
                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa sản phẩm", true);
                dialog.setSize(400, 500);
                dialog.setLayout(new GridLayout(11, 2, 5, 5));
                // Các phần tử
                JTextField txtTenSP = new JTextField(p.getTenSP());
                JTextField txtThuongHieu = new JTextField(p.getThuongHieu());
                JTextField txtXuatXu = new JTextField(p.getXuatXu());
                String[] loai = { "Hand-Winding", "Automatic", "Đồng hồ cơ lai" };
                JComboBox cbLoai = new JComboBox(loai);
                cbLoai.setSelectedItem(loai[p.getMaLoai() - 1]);
                JTextField txtGioiTinh = new JTextField(p.getGioiTinh());
                JTextField txtGiaBan = new JTextField(String.valueOf(p.getGiaBan()));
                JTextField txtSoLuong = new JTextField(String.valueOf(p.getSoLuong()));
                JTextField txtHinhAnh = new JTextField(p.getHinhAnh());
                JTextField txtMoTa = new JTextField(p.getMoTa());
                String[] ncc = { "Orient Japan", "Seiko Watch Corp" };
                JComboBox cbNCC = new JComboBox(ncc);
                // Thêm phần tử vào dialog
                dialog.add(new JLabel("Tên sản phẩm:"));
                dialog.add(txtTenSP);
                dialog.add(new JLabel("Thương hiệu:"));
                dialog.add(txtThuongHieu);
                dialog.add(new JLabel("Xuất xứ:"));
                dialog.add(txtXuatXu);
                dialog.add(new JLabel("Loại:"));
                dialog.add(cbLoai);
                dialog.add(new JLabel("Giới tính:"));
                dialog.add(txtGioiTinh);
                dialog.add(new JLabel("Giá bán:"));
                dialog.add(txtGiaBan);
                dialog.add(new JLabel("Số lượng:"));
                dialog.add(txtSoLuong);
                dialog.add(new JLabel("Hình ảnh:"));
                dialog.add(txtHinhAnh);
                dialog.add(new JLabel("Mô tả:"));
                dialog.add(txtMoTa);
                dialog.add(new JLabel("Nhà cung cấp:"));
                dialog.add(cbNCC);

                JButton btnSave = new JButton("Lưu");
                JButton btnCancel = new JButton("Hủy");

                dialog.add(btnSave);
                dialog.add(btnCancel);
                // Sự kiện nút Hủy
                btnCancel.addActionListener(e -> dialog.dispose());

                // Sự kiện nút Lưu
                btnSave.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        p.setTenSP(txtTenSP.getText());
                                        p.setThuongHieu(txtThuongHieu.getText());
                                        p.setXuatXu(txtXuatXu.getText());
                                        int maLoaiTmp = 0;
                                        switch (cbLoai.getSelectedItem().toString()) {
                                                case "Hand-Winding":
                                                        maLoaiTmp = 1;
                                                        break;
                                                case "Automatic":
                                                        maLoaiTmp = 2;
                                                        break;
                                                case "Đồng hồ cơ lai":
                                                        maLoaiTmp = 3;
                                                        break;
                                                default:
                                                        maLoaiTmp = 0;
                                                        break;
                                        }
                                        p.setMaLoai(maLoaiTmp);
                                        p.setGioiTinh(txtGioiTinh.getText());
                                        p.setGiaBan(Float.valueOf(txtGiaBan.getText()));
                                        p.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                                        p.setHinhAnh(txtHinhAnh.getText());
                                        p.setMoTa(txtMoTa.getText());
                                        int maNccTmp = 0;
                                        if (cbNCC.getSelectedItem() == "Orient Japan") {
                                                maNccTmp = 4;
                                        } else if (cbNCC.getSelectedItem() == "Seiko Watch Corp") {
                                                maNccTmp = 5;
                                        }
                                        ;
                                        p.setMaNCC(maNccTmp);
                                        SanPhamBUS bus = new SanPhamBUS();
                                        boolean result = bus.updateSanPham(p);

                                        if (result) {
                                                JOptionPane.showMessageDialog(dialog, "Cập nhật thành công!");
                                                loadDataToTable();
                                        } else {
                                                JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại!");
                                        }

                                        dialog.dispose();
                                } catch (NumberFormatException ex) {

                                        JOptionPane.showMessageDialog(dialog,
                                                        "Lỗi: 'Giá bán' và 'Số lượng' phải là số và không được để trống!",
                                                        "Lỗi đầu vào", JOptionPane.ERROR_MESSAGE);
                                }
                        }
                });

                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
        }

        private void openInsertDialog() {
                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm sản phẩm", true);
                dialog.setSize(400, 500);
                dialog.setLayout(new GridLayout(11, 2, 5, 5));
                // Các phần tử
                JTextField txtTenSP = new JTextField();
                JTextField txtThuongHieu = new JTextField();
                JTextField txtXuatXu = new JTextField();
                String[] loai = { "Hand-Winding", "Automatic", "Đồng hồ cơ lai" };
                JComboBox cbLoai = new JComboBox(loai);
                JTextField txtGioiTinh = new JTextField();
                JTextField txtGiaBan = new JTextField();
                JTextField txtSoLuong = new JTextField();
                JTextField txtHinhAnh = new JTextField();
                JTextField txtMoTa = new JTextField();
                String[] ncc = { "Orient Japan", "Seiko Watch Corp" };
                JComboBox cbNCC = new JComboBox(ncc);
                // Thêm phần tử vào dialog
                dialog.add(new JLabel("Tên sản phẩm:"));
                dialog.add(txtTenSP);
                dialog.add(new JLabel("Thương hiệu:"));
                dialog.add(txtThuongHieu);
                dialog.add(new JLabel("Xuất xứ:"));
                dialog.add(txtXuatXu);
                dialog.add(new JLabel("Loại:"));
                dialog.add(cbLoai);
                dialog.add(new JLabel("Giới tính:"));
                dialog.add(txtGioiTinh);
                dialog.add(new JLabel("Giá bán:"));
                dialog.add(txtGiaBan);
                dialog.add(new JLabel("Số lượng:"));
                dialog.add(txtSoLuong);
                dialog.add(new JLabel("Hình ảnh:"));
                dialog.add(txtHinhAnh);
                dialog.add(new JLabel("Mô tả:"));
                dialog.add(txtMoTa);
                dialog.add(new JLabel("Nhà cung cấp:"));
                dialog.add(cbNCC);

                JButton btnSave = new JButton("Lưu");
                JButton btnCancel = new JButton("Hủy");

                dialog.add(btnSave);
                dialog.add(btnCancel);

                // Sự kiện các nút
                btnSave.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                Product newp = new Product();
                                newp.setTenSP(txtTenSP.getText());
                                newp.setThuongHieu(txtThuongHieu.getText());
                                newp.setXuatXu(txtXuatXu.getText());
                                String loai = cbLoai.getSelectedItem().toString();
                                int maLoaiTmp = 0;
                                switch (loai) {
                                        case "Hand-Winding":
                                                maLoaiTmp = 1;
                                                break;
                                        case "Automatic":
                                                maLoaiTmp = 2;
                                                break;
                                        case "Đồng hồ cơ lai":
                                                maLoaiTmp = 3;
                                                break;
                                        default:
                                                maLoaiTmp = 0;
                                                break;
                                }

                                newp.setMaLoai(maLoaiTmp);
                                newp.setGioiTinh(txtGioiTinh.getText());
                                newp.setGiaBan(Float.valueOf(txtGiaBan.getText()));
                                newp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                                newp.setHinhAnh(txtHinhAnh.getText());
                                newp.setMoTa(txtMoTa.getText());
                                int maNccTmp = 0;
                                if (cbNCC.getSelectedItem() == "Orient Japan") {
                                        maNccTmp = 4;
                                } else if (cbNCC.getSelectedItem() == "Seiko Watch Corp") {
                                        maNccTmp = 5;
                                }
                                ;
                                newp.setMaNCC(maNccTmp);

                                SanPhamBUS bus = new SanPhamBUS();
                                int result = bus.insertSanPham(newp);
                                if (result > 0) {
                                        JOptionPane.showMessageDialog(dialog, "Thêm thành công!");
                                        ProductDetail pd = new ProductDetail(result);
                                        CTSanPhamBUS ctbus = new CTSanPhamBUS();
                                        boolean rs = ctbus.insertCTSanPham(pd);
                                        if (rs) {
                                                JOptionPane.showMessageDialog(dialog, "Tạo mới chi tiết!");
                                        }
                                        loadDataToTable();
                                } else {
                                        JOptionPane.showMessageDialog(dialog, "Thêm thất bại!");
                                }
                                dialog.dispose();
                        }
                });
                btnCancel.addActionListener(e -> dialog.dispose());
                // setvisible
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);

        }

        private ImageIcon loadAndScaleIcon(String path, int width, int height) {
                if (path == null || path.isEmpty()) {
                        System.err.println("Đường dẫn ảnh rỗng.");
                        return null; // Trả về null nếu đường dẫn rỗng
                }

                ImageIcon imageIcon = null;
                URL imgURL = getClass().getResource(path);

                // 1. Thử tải như một TÀI NGUYÊN (resource path, ví dụ: "/images/anh.png")
                if (imgURL != null) {
                        imageIcon = new ImageIcon(imgURL);
                }
                // 2. Nếu thất bại, thử tải như một TỆP (file system path, ví dụ:
                // "C:/images/anh.png")
                else {
                        File imgFile = new File(path);
                        if (imgFile.exists()) {
                                imageIcon = new ImageIcon(path);
                        } else {
                                // 3. Nếu cả hai đều thất bại, báo lỗi
                                System.err.println("Không thể tìm thấy ảnh: " + path);
                                return null; // Trả về null nếu không tìm thấy ảnh
                        }
                }

                // 4. Thu nhỏ ảnh nếu tải thành công
                if (imageIcon != null && imageIcon.getImage() != null) {
                        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        return new ImageIcon(image);
                }

                return null; // Trả về null nếu có lỗi
        }
}