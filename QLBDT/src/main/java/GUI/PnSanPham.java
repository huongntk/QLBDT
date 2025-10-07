package GUI;

import DTO.Product;
import DTO.ProductDetail;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.ProductDAO;

public class PnSanPham extends JPanel {
    ProductDAO pDAO = new ProductDAO();

    public PnSanPham() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel title = new JLabel("Danh sách sản phẩm:");
        title.setBounds(20, 20, 200, 20);
        add(title);

        JButton addProductBtn = new JButton("Thêm sản phẩm");
        addProductBtn.setBounds(480, 20, 120, 25);
        add(addProductBtn);

        JTextField searchTxt = new JTextField("Nhập tên sản phẩm");
        searchTxt.setBounds(20, 60, 150, 25);
        add(searchTxt);

        JButton searchBtn = new JButton("Tìm kiếm");
        searchBtn.setBounds(180, 60, 100, 25);
        add(searchBtn);

        String[] categories = { "Tất cả", "Ipad", "Iphone", "SamSung", "Oppo", "Xiaomi" };
        JComboBox<String> categoryCbx = new JComboBox<>(categories);
        categoryCbx.setBounds(480, 60, 120, 25);
        add(categoryCbx);
        ArrayList<Product> pList = pDAO.getALLProduct();
        JLabel tmp = new JLabel("Tổng sản phẩm: " + pList.size());
        tmp.setBounds(20, 560, 200, 25);
        add(tmp);
        String[] columnNames = { "ID", "Tên sản phẩm", "CategoryId", "SupplierId", "Số lượng", "Ảnh", "Giá", "Mô tả",
                "Trạng thái" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Product p : pList) {
            Object[] row = {
                    p.getProductId(),
                    p.getProductName(),
                    p.getCategoryId(),
                    p.getSupplierId(),
                    p.getQuantity(),
                    p.getImgUrl(),
                    p.getPrice(),
                    p.getDescription(),
                    p.getStatus()
            };
            model.addRow(row);
        }
        JTable table = new JTable(model);
        JScrollPane sc = new JScrollPane(table);
        sc.setBounds(20, 100, 1080, 320);
        add(sc);

        JButton viewDetailBtn = new JButton("Xem chi tiết");
        viewDetailBtn.setBounds(400, 560, 120, 25);
        add(viewDetailBtn);
        viewDetailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Product selectedProduct = pList.get(selectedRow);
                    new PnDetail(selectedProduct).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để xem chi tiết.");
                }
            }
        });
        JButton deleteProductBtn = new JButton("Xóa sản phẩm");
        deleteProductBtn.setBounds(240, 560, 120, 25);
        add(deleteProductBtn);
    }
}
