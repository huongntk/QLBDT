package GUI;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PnKhachHang extends JPanel {

    
    private final KhachHangBUS khachHangBUS;

    
    private JTextField txtMaKH, txtHo, txtTen, txtSdt;
    private JRadioButton radNam, radNu;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JTable tblKhachHang;
    private DefaultTableModel modelKhachHang;

    public PnKhachHang() {
        this.khachHangBUS = new KhachHangBUS();
        initComponents();
        addEvents();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(new Color(240, 247, 250));
        
        // === Form Nhập Liệu ===
        JPanel pnlForm = new JPanel(null);
        pnlForm.setBounds(20, 20, 1060, 150);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        add(pnlForm);

        JLabel lblMaKH = new JLabel("Mã KH:");
        lblMaKH.setBounds(20, 30, 60, 25);
        pnlForm.add(lblMaKH);

        txtMaKH = new JTextField();
        txtMaKH.setBounds(80, 30, 150, 25);
        txtMaKH.setEditable(false);
        pnlForm.add(txtMaKH);

        JLabel lblHo = new JLabel("Họ:");
        lblHo.setBounds(250, 30, 30, 25);
        pnlForm.add(lblHo);

        txtHo = new JTextField();
        txtHo.setBounds(290, 30, 200, 25);
        pnlForm.add(txtHo);

        JLabel lblTen = new JLabel("Tên:");
        lblTen.setBounds(510, 30, 40, 25);
        pnlForm.add(lblTen);

        txtTen = new JTextField();
        txtTen.setBounds(550, 30, 200, 25);
        pnlForm.add(txtTen);

        JLabel lblSdt = new JLabel("SĐT:");
        lblSdt.setBounds(20, 80, 60, 25);
        pnlForm.add(lblSdt);

        txtSdt = new JTextField();
        txtSdt.setBounds(80, 80, 150, 25);
        pnlForm.add(txtSdt);
        
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setBounds(250, 80, 60, 25);
        pnlForm.add(lblGioiTinh);

        radNam = new JRadioButton("Nam");
        radNam.setBounds(320, 80, 60, 25);
        radNu = new JRadioButton("Nữ");
        radNu.setBounds(390, 80, 60, 25);
        ButtonGroup groupGioiTinh = new ButtonGroup();
        groupGioiTinh.add(radNam);
        groupGioiTinh.add(radNu);
        pnlForm.add(radNam);
        pnlForm.add(radNu);

        btnThem = new JButton("Thêm");
        btnThem.setBounds(800, 25, 100, 30);
        pnlForm.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(800, 65, 100, 30);
        pnlForm.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(920, 25, 100, 30);
        pnlForm.add(btnXoa);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBounds(920, 65, 100, 30);
        pnlForm.add(btnLamMoi);

        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 190, 1060, 380);
        add(scrollPane);

        tblKhachHang = new JTable();
        modelKhachHang = new DefaultTableModel(
                new Object[]{"Mã KH", "Họ", "Tên", "Giới tính", "Số điện thoại", "Tổng chi tiêu", "Tình trạng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tblKhachHang.setModel(modelKhachHang);
        scrollPane.setViewportView(tblKhachHang);
    }
    
  
    private void loadDataToTable() {
        modelKhachHang.setRowCount(0); 
        ArrayList<KhachHangDTO> dsKhachHang = khachHangBUS.getAllKhachHang();
        
        for (KhachHangDTO kh : dsKhachHang) {
            
            if (kh.isTinhTrang()) {
                Object[] row = {
                        kh.getMaKH(),
                        kh.getHo(),
                        kh.getTen(),
                        kh.getGioiTinh(),
                        kh.getSoDienThoai(),
                        kh.getTongChiTieu(),
                        kh.isTinhTrang() ? "Hoạt động" : "Bị ẩn"
                };
                modelKhachHang.addRow(row);
            }
        }
    }
    
 
    private void addEvents() {
        
        tblKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblKhachHang.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaKH.setText(modelKhachHang.getValueAt(selectedRow, 0).toString());
                    txtHo.setText(modelKhachHang.getValueAt(selectedRow, 1).toString());
                    txtTen.setText(modelKhachHang.getValueAt(selectedRow, 2).toString());
                    
                    String gioiTinh = modelKhachHang.getValueAt(selectedRow, 3).toString();
                    if (gioiTinh.equalsIgnoreCase("Nam")) {
                        radNam.setSelected(true);
                    } else {
                        radNu.setSelected(true);
                    }
                    
                    txtSdt.setText(modelKhachHang.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        btnLamMoi.addActionListener(e -> clearForm());

        btnThem.addActionListener(e -> {
            KhachHangDTO kh = getFormData();
            if (kh != null) {
                String result = khachHangBUS.addKhachHang(kh);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    clearForm();
                }
            }
        });

        
        btnSua.addActionListener(e -> {
            int selectedRow = tblKhachHang.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!");
                return;
            }
            KhachHangDTO kh = getFormData();
            if (kh != null) {
                String result = khachHangBUS.updateKhachHang(kh);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    clearForm();
                }
            }
        });

        
        btnXoa.addActionListener(e -> {
            int selectedRow = tblKhachHang.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int maKH = Integer.parseInt(txtMaKH.getText());
                String result = khachHangBUS.deleteKhachHang(maKH);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    clearForm();
                }
            }
        });
    }

 
    private KhachHangDTO getFormData() {
        KhachHangDTO kh = new KhachHangDTO();
        if (!txtMaKH.getText().isEmpty()) {
            kh.setMaKH(Integer.parseInt(txtMaKH.getText()));
        }
        kh.setHo(txtHo.getText().trim());
        kh.setTen(txtTen.getText().trim());
        kh.setSoDienThoai(txtSdt.getText().trim());
        if (radNam.isSelected()) {
            kh.setGioiTinh("Nam");
        } else if (radNu.isSelected()) {
            kh.setGioiTinh("Nữ");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return null;
        }
        return kh;
    }

   
    private void clearForm() {
        txtMaKH.setText("");
        txtHo.setText("");
        txtTen.setText("");
        txtSdt.setText("");
        radNam.setSelected(false);
        radNu.setSelected(false);
        tblKhachHang.clearSelection();
    }
}

    

