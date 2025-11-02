/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTIL;

public class Auth {
    public static int maNV;
    public static String quyen;

    public static boolean isAdmin() {
        return "Admin".equalsIgnoreCase(quyen);
    }

    public static boolean isBanHang() {
        return "BanHang".equalsIgnoreCase(quyen);
    }
}

