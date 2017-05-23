/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *管理员业务处理类
 * @author Administrator
 */
public class AdminManager {
    private String sql = null;
    
    private PreparedStatement getPreparedStatement(String sql)throws SQLException, ClassNotFoundException{
        return new DBConnecter().getConnection().prepareStatement(sql);
    }
            
    public boolean login(Admin admin)throws SQLException, ClassNotFoundException{
        sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1, admin.getName());
        ps.setString(2,admin.getPassword());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    
}
