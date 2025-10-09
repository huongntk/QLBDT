/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DTO.Product;
import UTIL.DBConnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author ltd96
 * PRODUCT DATA ACCESS OBJECT
 */
public class SanphamDAO {
    public ArrayList<Product> getALL() {
        ArrayList<Product> listTmp = new ArrayList();
        try(Connection conn =  DBConnect.getConnection()){
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM SanPham;");
            while(rs.next()){
                Product p = new Product();
                p.setID(rs.getInt("ID"));
                p.setTenSP(rs.getString("TenSP"));
                p.setThuongHieu(rs.getString("ThuongHieu"));
                p.setXuatXu(rs.getString("XuatXu"));
                p.setMaLoai(rs.getInt("MaLoai"));
                p.setGioiTinh(rs.getString("GioiTinh"));
                p.setGiaBan(rs.getFloat("GiaBan"));
                p.setSoLuong(rs.getInt("SoLuong"));
                p.setHinhAnh(rs.getString("HinhAnh"));
                p.setMoTa(rs.getString("MoTa"));
                p.setMaNCC(rs.getInt("maNCC"));
                p.setTrangThai(rs.getBoolean("TrangThai"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    return listTmp;
    }
    
}
