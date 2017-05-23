/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *旅客业务处理类
 * @author Administrator
 */
public class CustomerManager {
    private String sql = null;
    
    private PreparedStatement getPreparedStatement(String sql) throws SQLException, ClassNotFoundException{
        return new DBConnecter().getConnection().prepareStatement(sql);
    }
    //旅客登记
    public boolean addCustomer(Customer customer){
        sql = "INSERT INTO customer"+"(name,card,room,money,time,sex,hometown)"+"VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement ps = getPreparedStatement(sql);
            
            ps.setString(1,customer.getName());
            ps.setString(2,customer.getCard());
            ps.setInt(3,customer.getRoom());
            ps.setFloat(4,customer.getMoney());
            ps.setDate(5, (Date) customer.getTime());
            ps.setString(6,customer.getSex());
            ps.setString(7,customer.getHometown());
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    //删除旅客
    public boolean delete(int id) throws ClassNotFoundException{
        try{
            sql = "DELETE FROM customer WHERE id=?";
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    //追加押金
    public boolean addMoney(float money,int roomId) throws ClassNotFoundException{
        try{
            sql = "UPDATE customer SET money=money+? WHERE room=?";
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setFloat(1,money);
            ps.setInt(2, roomId);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    //调房间
    public boolean change(int oldroomId,int newroomId) throws ClassNotFoundException{
        sql = "UPDATE customer SET room = ? WHERE room=?";
        try{
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1,newroomId);
            ps.setInt(2, oldroomId);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    //查询旅客;ByRoomId
    public Customer getCustomerByRoomId(int roomId ) throws ClassNotFoundException{
        Customer c = null;
        try{
            sql = "SELECT * FROM customer WHERE room=?";
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1,roomId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c = new Customer();
                c.setCard(rs.getString("card"));
                c.setHometown(rs.getString("hometown"));
                c.setId(rs.getInt("id"));
                c.setMoney(rs.getFloat("money"));
                c.setName(rs.getString("name"));
                c.setRoom(rs.getInt("room"));
                c.setSex(rs.getString("sex"));
                c.setTime(rs.getDate("time"));
            }
            return c;     
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return c;
        }
    }
    //查询旅客;ByCard
    public Customer getCustomerByCard(String card ) throws ClassNotFoundException{
        Customer c = null;
        try{
            sql = "SELECT * FROM customer WHERE card=?";
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setString(1,card);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c = new Customer();
                c.setCard(rs.getString("card"));
                c.setHometown(rs.getString("hometown"));
                c.setId(rs.getInt("id"));
                c.setMoney(rs.getFloat("money"));
                c.setName(rs.getString("name"));
                c.setRoom(rs.getInt("room"));
                c.setSex(rs.getString("sex"));
                c.setTime(rs.getDate("time"));
            }
            return c;     
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return c;
        }
    }
    //查询旅客;ByName
    public Customer getCustomerByName(String name ) throws ClassNotFoundException{
        Customer c = null;
        try{
            sql = "SELECT * FROM customer WHERE name=?";
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c = new Customer();
                c.setCard(rs.getString("card"));
                c.setHometown(rs.getString("hometown"));
                c.setId(rs.getInt("id"));
                c.setMoney(rs.getFloat("money"));
                c.setName(rs.getString("name"));
                c.setRoom(rs.getInt("room"));
                c.setSex(rs.getString("sex"));
                c.setTime(rs.getDate("time"));
            }
            return c;     
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return c;
        }
    }
    
}
