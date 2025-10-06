/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTIL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author PC
 */
public class ConnectDB {
    private static final String URL = "jdbc:sqlserver://localhost:3306;DatabaseName=QLBanDienThoai";
    private static final String USER = "sa";
    private static final String PASS = "12345";
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Driver SQL Server!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Kết nối CSDL thất bại!");
            e.printStackTrace();
        }
        return con;
    }
}
