/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *房间业务处理类
 * @author Administrator
 */
public class RoomManager {
    private String sql = null;
    private PreparedStatement getPreparedStatement(String sql) throws SQLException, ClassNotFoundException{
        return new DBConnecter().getConnection().prepareStatement(sql);
    }
    class MyDefaultTableModel extends DefaultTableModel{
        public MyDefaultTableModel(Object[][] data,Object[] columnNames){
            super(data,columnNames);
        }
        @Override
        public boolean isCellEditable(int rowIndex,int columnindex){
            return false;
        }
    }
    //1.设置新房间
    public boolean addRoom(Room room) throws ClassNotFoundException{
        sql = "INSERT INTO room"+"(rmid,grade,price,state,remarks)"+"VALUES(?,?,?,0,'可入住')";
        try{
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1,room.getId());
            ps.setString(2,room.getGrade());
            ps.setFloat(3,room.getPrice());          
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }        
    }
    //2.查询房间
    public Room getRoom(int id) throws ClassNotFoundException{
        try{
            ResultSet rs = this.getCustomers(id);
            if(rs.next()){
                Room room = new Room();
                room.setGrade(rs.getString("grade"));
                room.setId(rs.getInt("rmid"));
                room.setPrice(rs.getFloat("price"));
                room.setRemarks(rs.getString("remarks"));
                room.setState(rs.getInt("state"));
                return room;
            }
            return null;
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }
    //更改房间状态： 置空  state = 0
    public boolean setN(int id) throws ClassNotFoundException{
        sql ="UPDATE room SET state=0 ,remarks='可入住' WHERE rmid=?";
        try{
            PreparedStatement ps = getPreparedStatement(sql);
            
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
            return true;
            
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }      
    }
    //更改房间状态： 置满 state = 1 
    public boolean setY(int id) throws ClassNotFoundException{
        sql ="UPDATE room SET state=1 ,remarks='有人' WHERE rmid=?";
        try{
            PreparedStatement ps = getPreparedStatement(sql);
            
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
            return true;
            
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }      
    }
    //4.查询所有房间信息
    public DefaultTableModel getMessage() throws ClassNotFoundException{
        try{
            return toDefaultTableModel(toArrayList(getCustomers()));
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }
    public DefaultTableModel toDefaultTableModel(ArrayList<Room> al){
        int row = al.size();
        Object[][] o = new Object[row][5];
        Room room =null;
        for(int i=0;i<row;i++){
            room = al.get(i);
            for(int k=0;k<5;k++){
                if(k==0){
                    o[i][k] = Integer.valueOf(room.getId());
                }else if(k==1){
                    o[i][k] = room.getGrade();
                }else if(k==2){
                    o[i][k] = Float.valueOf(room.getPrice());
                }else if(k==3){
                    o[i][k] = room.getState()==1 ? "有人":"空";
                }else{
                    o[i][k] = room.getRemarks();
                }
            }
        }
        String col[] = {"房间","级别","价格","状态","备注"};
        return new MyDefaultTableModel(o,col);
    }
    private ArrayList<Room> toArrayList(ResultSet rs){
        try{
            ArrayList<Room> al = new ArrayList<Room>();
            Room room = null;
            while (rs.next()){
                room = new Room();              
                room.setId(rs.getInt("rmid"));
                room.setGrade(rs.getString("grade"));
                room.setPrice(rs.getFloat("price"));
                room.setState(rs.getInt("state"));
                room.setRemarks(rs.getString("remarks"));
                al.add(room);
            }
            rs.close();
            return al;
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }
    private ResultSet getCustomers() throws SQLException, ClassNotFoundException{
        sql = "SELECT * FROM room";
        PreparedStatement ps = getPreparedStatement(sql);
        return ps.executeQuery();
    }
    //5.按特定房间号查询
    public DefaultTableModel getMessage(int id) throws ClassNotFoundException{
        try{
            return toDefaultTableModel(toArrayList(getCustomers(id)));
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }
    private ResultSet getCustomers(int id) throws SQLException, ClassNotFoundException{
        sql = "SELECT * FROM room WHERE rmid = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }
    //6.按房间级别与状态查询
    public DefaultTableModel getMessage(String grade,int state) throws ClassNotFoundException{
        try{
            return toDefaultTableModel(toArrayList(getCustomers(grade,state)));
        }catch(SQLException ex){
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }
    private ResultSet getCustomers(String grade,int state) throws SQLException, ClassNotFoundException{
        sql = "SELECT * FROM room WHERE grade = ? AND state=?";
        PreparedStatement ps = getPreparedStatement(sql);
        
        ps.setString(1, grade);
        ps.setInt(2, state);
        return ps.executeQuery();
    }
}
