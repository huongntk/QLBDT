/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.PhanQuyenBUS;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class PnPhanQuyen extends javax.swing.JPanel{

    PhanQuyenBUS pqBUS = new PhanQuyenBUS();
    
    public PnPhanQuyen() {
        initComponents();
        loadDanhSachQuyen();
    }
    private void loadDanhSachQuyen() {
        try {
            ResultSet rs = pqBUS.layDanhSachQuyen();
            while (rs.next()) {
                cboQuyen.addItem(rs.getString("Quyen"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlThanhCongCu4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        pnlCenter = new javax.swing.JPanel();
        cboQuyen = new javax.swing.JComboBox<>();
        chkBanHang = new javax.swing.JCheckBox();
        chkNhanVien = new javax.swing.JCheckBox();
        chkNhapHang = new javax.swing.JCheckBox();
        chkKhuyenMai = new javax.swing.JCheckBox();
        chkThongKe = new javax.swing.JCheckBox();
        chkSanPham = new javax.swing.JCheckBox();
        chkKhachHang = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        pnlThanhCongCu4.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 255), new java.awt.Color(204, 204, 204)));
        pnlThanhCongCu4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThem.setText("THÊM");
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlThanhCongCu4.add(btnThem);

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sua.png"))); // NOI18N
        btnSua.setText("SỬA");
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        pnlThanhCongCu4.add(btnSua);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/xoa.png"))); // NOI18N
        btnXoa.setText("XÓA");
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlThanhCongCu4.add(btnXoa);

        add(pnlThanhCongCu4, java.awt.BorderLayout.NORTH);

        pnlCenter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCenter.setMaximumSize(new java.awt.Dimension(146, 368));
        pnlCenter.setPreferredSize(new java.awt.Dimension(278, 582));
        pnlCenter.setLayout(new javax.swing.BoxLayout(pnlCenter, javax.swing.BoxLayout.Y_AXIS));

        cboQuyen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboQuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Chọn quyền --" }));
        cboQuyen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cboQuyen.setMaximumSize(new java.awt.Dimension(180, 30));
        cboQuyen.setPreferredSize(new java.awt.Dimension(180, 26));
        cboQuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboQuyenActionPerformed(evt);
            }
        });
        pnlCenter.add(cboQuyen);

        chkBanHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkBanHang.setText("Quản lý bán hàng");
        chkBanHang.setMargin(new java.awt.Insets(8, 8, 8, 8));
        chkBanHang.setRolloverEnabled(false);
        pnlCenter.add(chkBanHang);

        chkNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkNhanVien.setText("Quản lý nhân viên");
        chkNhanVien.setMargin(new java.awt.Insets(8, 8, 8, 8));
        pnlCenter.add(chkNhanVien);

        chkNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkNhapHang.setText("Quản lý nhập hàng");
        chkNhapHang.setMargin(new java.awt.Insets(8, 8, 8, 8));
        pnlCenter.add(chkNhapHang);

        chkKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkKhuyenMai.setText("Quản lý khuyến mãi");
        chkKhuyenMai.setMargin(new java.awt.Insets(8, 8, 8, 8));
        chkKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkKhuyenMaiActionPerformed(evt);
            }
        });
        pnlCenter.add(chkKhuyenMai);

        chkThongKe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkThongKe.setText("Quản lý thống kê");
        chkThongKe.setMargin(new java.awt.Insets(8, 8, 8, 8));
        pnlCenter.add(chkThongKe);

        chkSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkSanPham.setText("Quản lý sản phẩm");
        chkSanPham.setMargin(new java.awt.Insets(8, 8, 8, 8));
        pnlCenter.add(chkSanPham);

        chkKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkKhachHang.setText("Quản lý khách hàng");
        chkKhachHang.setMargin(new java.awt.Insets(8, 8, 8, 8));
        chkKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkKhachHangActionPerformed(evt);
            }
        });
        pnlCenter.add(chkKhachHang);

        add(pnlCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        String Quyen = (String) cboQuyen.getSelectedItem();
        if (Quyen == null || Quyen.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn quyền!");
            return;
        }

        boolean qlBanHang = chkBanHang.isSelected();
        boolean qlNhapHang = chkNhapHang.isSelected();
        boolean qlNhanVien = chkNhanVien.isSelected();
        boolean qlSanPham = chkSanPham.isSelected();
        boolean qlKhachHang = chkKhachHang.isSelected();
        boolean qlKhuyenMai = chkKhuyenMai.isSelected();
        boolean qlThongKe = chkThongKe.isSelected();

        boolean kq = pqBUS.themHoacCapNhatQuyen(Quyen, qlBanHang, qlNhapHang, qlNhanVien, qlSanPham, qlKhachHang, qlKhuyenMai, qlThongKe);

        if (kq) {
            JOptionPane.showMessageDialog(this, "Lưu quyền thành công!");

        } else {
            JOptionPane.showMessageDialog(this, "Lưu quyền thất bại!");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void chkKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkKhachHangActionPerformed

    private void chkKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkKhuyenMaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkKhuyenMaiActionPerformed

    private void cboQuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboQuyenActionPerformed
        String quyenDuocChon = (String) cboQuyen.getSelectedItem();
        if (quyenDuocChon==null  || quyenDuocChon.equals("")) return;
        if (quyenDuocChon != null ) {
            boolean[] dsQuyen = pqBUS.layQuyen(quyenDuocChon);

            chkBanHang.setSelected(dsQuyen[0]);
            chkNhapHang.setSelected(dsQuyen[1]);
            chkNhanVien.setSelected(dsQuyen[2]);
            chkSanPham.setSelected(dsQuyen[3]);
            chkKhuyenMai.setSelected(dsQuyen[4]);
            chkThongKe.setSelected(dsQuyen[5]);
            chkKhachHang.setSelected(dsQuyen[6]);
        }
    }//GEN-LAST:event_cboQuyenActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        String Quyen = (String) cboQuyen.getSelectedItem();
        if (Quyen == null || Quyen.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn quyền!");
            return;
        }

        boolean qlBanHang = chkBanHang.isSelected();
        boolean qlNhapHang = chkNhapHang.isSelected();
        boolean qlNhanVien = chkNhanVien.isSelected();
        boolean qlSanPham = chkSanPham.isSelected();
        boolean qlKhachHang = chkKhachHang.isSelected();
        boolean qlKhuyenMai = chkKhuyenMai.isSelected();
        boolean qlThongKe = chkThongKe.isSelected();

        boolean kq = pqBUS.themHoacCapNhatQuyen(Quyen, qlBanHang, qlNhapHang, qlNhanVien, qlSanPham, qlKhachHang, qlKhuyenMai, qlThongKe);

        if (kq) {
            JOptionPane.showMessageDialog(this, "Lưu quyền thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Lưu quyền thất bại!");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        String Quyen = (String) cboQuyen.getSelectedItem();
        if (Quyen == null || Quyen.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn quyền cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa quyền \"" + Quyen + "\"?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            boolean kq = pqBUS.xoaQuyen(Quyen);

            if (kq) {
                JOptionPane.showMessageDialog(this, "Xóa quyền thành công!");
                cboQuyen.removeItem(Quyen); // Cập nhật lại ComboBox
            } else {
                JOptionPane.showMessageDialog(this, "Xóa quyền thất bại!");
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboQuyen;
    private javax.swing.JCheckBox chkBanHang;
    private javax.swing.JCheckBox chkKhachHang;
    private javax.swing.JCheckBox chkKhuyenMai;
    private javax.swing.JCheckBox chkNhanVien;
    private javax.swing.JCheckBox chkNhapHang;
    private javax.swing.JCheckBox chkSanPham;
    private javax.swing.JCheckBox chkThongKe;
    private javax.swing.JPanel pnlCenter;
    private javax.swing.JPanel pnlThanhCongCu4;
    // End of variables declaration//GEN-END:variables
}
