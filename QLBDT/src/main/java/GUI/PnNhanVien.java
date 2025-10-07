package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PnNhanVien extends JPanel {

    
    private final NhanVienBUS nhanVienBUS;

    // --- Components ---
    private JTextField txtMaNV, txtHo, txtTen, txtSdt, txtChucVu;
    private JRadioButton radNam, radNu;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JTable tblNhanVien;
    private DefaultTableModel modelNhanVien;

    public PnNhanVien() {
        this.nhanVienBUS = new NhanVienBUS();
        initComponents();
        addEvents();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(new Color(240, 247, 250));

        JPanel pnlForm = new JPanel(null);
        pnlForm.setBounds(20, 20, 1060, 150);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        add(pnlForm);

        JLabel lblMaNV = new JLabel("Mã NV:");
        lblMaNV.setBounds(20, 30, 60, 25);
        pnlForm.add(lblMaNV);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(80, 30, 150, 25);
        txtMaNV.setEditable(false);
        pnlForm.add(txtMaNV);

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
        
        JLabel lblChucVu = new JLabel("Chức vụ:");
        lblChucVu.setBounds(510, 80, 60, 25);
        pnlForm.add(lblChucVu);
        
        txtChucVu = new JTextField();
        txtChucVu.setBounds(570, 80, 180, 25);
        pnlForm.add(txtChucVu);


       
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

        tblNhanVien = new JTable();
        modelNhanVien = new DefaultTableModel(
                new Object[]{"Mã NV", "Họ", "Tên", "Giới tính", "SĐT", "Chức vụ", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblNhanVien.setModel(modelNhanVien);
        scrollPane.setViewportView(tblNhanVien);
    }
    
    private void loadDataToTable() {
        modelNhanVien.setRowCount(0);
       
        ArrayList<NhanVienDTO> dsNhanVien = nhanVienBUS.getAllNhanVien(); 

        for (NhanVienDTO nv : dsNhanVien) {
            if (nv.isTrangThai()) {
                Object[] row = {
                        nv.getMaNV(),
                        nv.getHo(),
                        nv.getTen(),
                        nv.getGioiTinh(),
                        nv.getSoDienThoai(),
                        nv.getChucVu(),
                        nv.isTrangThai() ? "Hoạt động" : "Nghỉ việc"
                };
                modelNhanVien.addRow(row);
            }
        }
    }

    private void addEvents() {
        tblNhanVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblNhanVien.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaNV.setText(modelNhanVien.getValueAt(selectedRow, 0).toString());
                    txtHo.setText(modelNhanVien.getValueAt(selectedRow, 1).toString());
                    txtTen.setText(modelNhanVien.getValueAt(selectedRow, 2).toString());
                    
                    String gioiTinh = modelNhanVien.getValueAt(selectedRow, 3).toString();
                    if (gioiTinh.equalsIgnoreCase("Nam")) {
                        radNam.setSelected(true);
                    } else {
                        radNu.setSelected(true);
                    }
                    
                    txtSdt.setText(modelNhanVien.getValueAt(selectedRow, 4).toString());
                    txtChucVu.setText(modelNhanVien.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        btnLamMoi.addActionListener(e -> clearForm());

        btnThem.addActionListener(e -> {
            NhanVienDTO nv = getFormData();
            if (nv != null) {
               
                String result = nhanVienBUS.addNhanVien(nv); 
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    clearForm();
                }
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = tblNhanVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
                return;
            }
            NhanVienDTO nv = getFormData();
            if (nv != null) {
                String result = nhanVienBUS.updateNhanVien(nv);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    clearForm();
                }
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tblNhanVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int maNV = Integer.parseInt(txtMaNV.getText());
                String result = nhanVienBUS.deleteNhanVien(maNV);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    clearForm();
                }
            }
        });
    }

    private NhanVienDTO getFormData() {
        NhanVienDTO nv = new NhanVienDTO();
        if (!txtMaNV.getText().isEmpty()) {
            nv.setMaNV(Integer.parseInt(txtMaNV.getText()));
        }
        nv.setHo(txtHo.getText().trim());
        nv.setTen(txtTen.getText().trim());
        nv.setSoDienThoai(txtSdt.getText().trim());
        nv.setChucVu(txtChucVu.getText().trim());

        if (radNam.isSelected()) {
            nv.setGioiTinh("Nam");
        } else if (radNu.isSelected()) {
            nv.setGioiTinh("Nữ");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return null;
        }
        return nv;
    }

    private void clearForm() {
        txtMaNV.setText("");
        txtHo.setText("");
        txtTen.setText("");
        txtSdt.setText("");
        txtChucVu.setText("");
        radNam.setSelected(false);
        radNu.setSelected(false);
        tblNhanVien.clearSelection();
    }
}