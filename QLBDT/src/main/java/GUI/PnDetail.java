package GUI;

import javax.swing.*;

import DTO.Product;

import java.awt.*;

public class PnDetail extends JFrame {

    private JLabel lblMaSP, lblTenSP, lblLoai, lblNCC, lblSoLuong,
            lblGia, lblMoTa, lblTrangThai, lblHinhAnh;

    private JTextField txtMaSP, txtTenSP, txtLoai, txtNCC, txtSoLuong,
            txtGia, txtTrangThai;
    private JTextArea txtMoTa;
    private JLabel imgLabel;
    private Product p;

    public PnDetail(Product p) {
        this.p = p;
        setTitle("Chi tiết sản phẩm");

        initComponents();
    }

    public void initComponents() {
        setLayout(null);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        int labelX = 30, labelW = 100, fieldX = 150, fieldW = 450;
        int y = 30, gap = 45, height = 30;

        // --- Mã sản phẩm ---
        lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(labelX, y, labelW, height);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(fieldX, y, fieldW, height);
        txtMaSP.setText(String.valueOf(p.getProductId()));
        txtMaSP.setEditable(false);
        add(lblMaSP);
        add(txtMaSP);

        // --- Tên sản phẩm ---
        y += gap;
        lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(labelX, y, labelW, height);
        txtTenSP = new JTextField(String.valueOf(p.getProductName()));
        txtTenSP.setBounds(fieldX, y, fieldW, height);
        txtTenSP.setEditable(false);
        add(lblTenSP);
        add(txtTenSP);

        // --- Loại sản phẩm ---
        y += gap;
        lblLoai = new JLabel("Loại:");
        lblLoai.setBounds(labelX, y, labelW, height);
        txtLoai = new JTextField(String.valueOf(p.getCategoryId()));
        txtLoai.setBounds(fieldX, y, fieldW, height);
        txtLoai.setEditable(false);
        add(lblLoai);
        add(txtLoai);

        // --- Nhà cung cấp ---
        y += gap;
        lblNCC = new JLabel("Nhà cung cấp:");
        lblNCC.setBounds(labelX, y, labelW, height);
        txtNCC = new JTextField(String.valueOf(p.getSupplierId()));
        txtNCC.setBounds(fieldX, y, fieldW, height);
        txtNCC.setEditable(false);
        add(lblNCC);
        add(txtNCC);

        // --- Số lượng ---
        y += gap;
        lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(labelX, y, labelW, height);
        txtSoLuong = new JTextField(String.valueOf(p.getQuantity()));
        txtSoLuong.setBounds(fieldX, y, fieldW, height);
        txtSoLuong.setEditable(false);
        add(lblSoLuong);
        add(txtSoLuong);

        // --- Đơn giá ---
        y += gap;
        lblGia = new JLabel("Giá bán:");
        lblGia.setBounds(labelX, y, labelW, height);
        txtGia = new JTextField(String.valueOf(p.getPrice()));
        txtGia.setBounds(fieldX, y, fieldW, height);
        txtGia.setEditable(false);
        add(lblGia);
        add(txtGia);

        // --- Trạng thái ---
        y += gap;
        lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(labelX, y, labelW, height);
        txtTrangThai = new JTextField(p.getStatus() ? "Còn hàng" : "Hết hàng");
        txtTrangThai.setBounds(fieldX, y, fieldW, height);
        txtTrangThai.setEditable(false);
        add(lblTrangThai);
        add(txtTrangThai);

        // --- Mô tả ---
        y += gap;
        lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setBounds(labelX, y, labelW, height);
        txtMoTa = new JTextArea(String.valueOf(p.getDescription()));
        txtMoTa.setBounds(fieldX, y, fieldW, 100);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setEditable(false);
        JScrollPane sp = new JScrollPane(txtMoTa);
        sp.setBounds(fieldX, y, fieldW, 100);
        add(lblMoTa);
        add(sp);

        // --- Hình ảnh sản phẩm ---
        y += 130;
        lblHinhAnh = new JLabel("Hình ảnh:");
        lblHinhAnh.setBounds(labelX, y, labelW, height);
        add(lblHinhAnh);

        imgLabel = new JLabel();
        imgLabel.setBounds(fieldX, y, 150, 150);
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(imgLabel);
    }

}
