package GUI;

import javax.swing.*;
import java.awt.*;

public class PnNhapHang extends JPanel {

    private final JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);

    // 2 panel con của bạn — ĐỀU extends JPanel rồi
    private final PnPhieuNhap pnPhieuNhap = new PnPhieuNhap();
    private final PnNhaCungCap pnNhaCungCap = new PnNhaCungCap();

    public PnNhapHang() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        // (tuỳ chọn) chống co cụt khi dùng GroupLayout bên trong panel con
        pnPhieuNhap.setPreferredSize(new Dimension(1000, 600));
        pnNhaCungCap.setPreferredSize(new Dimension(1000, 600));

        tabs.addTab("Phiếu nhập", pnPhieuNhap);
        tabs.addTab("Nhà cung cấp", pnNhaCungCap);

        add(tabs, BorderLayout.CENTER);
    }

    // tiện ích nếu bạn muốn nhảy tab từ nơi khác
    public void showPhieuNhapTab()  { tabs.setSelectedIndex(0); }
    public void showNhaCungCapTab() { tabs.setSelectedIndex(1); }
}
