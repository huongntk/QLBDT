package UTIL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    // ✅ Thông tin kết nối SQL Server
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanDienThoai;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    // ✅ Hàm trả về đối tượng Connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối CSDL thành công!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy driver SQL Server!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối CSDL!");
            e.printStackTrace();
        }
        return conn;
    }
}
