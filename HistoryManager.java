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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *历史记录业务处理类
 * @author Administrator
 */
public class HistoryManager {
    String sql;
    private PreparedStatement getPreparedStatement(String sql) throws SQLException, ClassNotFoundException {
        return new DBConnecter().getConnection().prepareStatement(sql);
        
    }
    //内部类，继承DefaultTableModel
    class MyDefaultTableModel extends DefaultTableModel{
        public MyDefaultTableModel(Object[][] data,Object[] columnNames){
            super(data,columnNames);
        }
        @Override
        public boolean isCellEditable(int rowIndex ,int columnIndex){
            return false;
        }
    }
    //1.添加历史记录
    public boolean addHistory(History hs){
        sql = "INSERT INTO history"+"(name,room,registerTime,exitTime,money,remarks)"+"VALUES(?,?,?,?,?,?)";
        try{
            System.out.print("ok\t");
            PreparedStatement ps = getPreparedStatement(sql);          
            ps.setString(1, hs.getName());
                System.out.print(hs.getName()+"\t");
            ps.setInt(2, hs.getRoom());
                System.out.print(hs.getRoom()+"\t");
            ps.setDate(3, (Date) hs.getRegisterTime());
                System.out.print(hs.getRegisterTime()+"\t");
            ps.setDate(4, (Date) hs.getExitTime());
                System.out.print(hs.getExitTime()+"\t");
            ps.setFloat(5, hs.getMoney());
                System.out.print(hs.getMoney()+"\t");
            ps.setString(6, hs.getRemarks());
                System.out.print(hs.getRemarks()+"\t");
            ps.executeUpdate();
            ps.close();
            return true;
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            return false;
        }
    }
    //2.通过ID查询历史记录
    public History getHistory(int id) throws SQLException, ClassNotFoundException{
        sql = "SELECT * FROM history WHERE id=?";
        PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        History hs = null;
        if(rs.next()){
            hs = new History();
            hs.setExitTime(rs.getDate("exitTime"));
            hs.setId(rs.getInt("id"));
            hs.setMoney(rs.getFloat("money"));
            hs.setName(rs.getString("name"));
            hs.setRegisterTime(rs.getDate("registerTime"));
            hs.setRemarks(rs.getString("remarks"));
            hs.setRoom(rs.getInt("room"));
            
        }
        return hs;
    }   
    //3.有条件查询历史记录
    @SuppressWarnings("ConvertToStringSwitch")
    public DefaultTableModel getDefaultTableModel(String type) throws ClassNotFoundException{
        if (type.equals("all")){
            try{
                return toDefaultTableModel(this.toArrayList(this.getHistorys())) ;        
            }catch(SQLException ex){
                Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE,null,ex);
                return null;
            }
        }else if(type.equals("owe")){
            try{              
                return toDefaultTableModel(this.toArrayList(this.getNoHistorys())) ;            
            }catch(SQLException ex){
                Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE,null,ex);
                return null; 
            }            
        }else{
            return null;
        }
    }
    public DefaultTableModel toDefaultTableModel(ArrayList<History> al){
        int row = al.size();
        Object[][] o = new Object[row][7];
        History hs;
        for(int i=0;i<row;i++){
            hs = al.get(i);    //循环获取History对象
            for(int k=0;k<7;k++){   
                if(k==0){
                    o[i][k] = hs.getId();                  
                }else if(k==1){
                    o[i][k] = hs.getName();
                }else if(k==2){
                    o[i][k] = hs.getRoom();                 
                }else if(k==3){
                    o[i][k] = hs.getRegisterTime();                  
                }else if(k==4){
                    o[i][k] = hs.getExitTime();                   
                }else if(k==5){
                    o[i][k] = hs.getMoney();                    
                }else{
                    o[i][k] = hs.getRemarks();
                }
            }      
        }
        String col[] ={"记录编号","旅客姓名","房间号","登记时间","退房时间","消费金额","备注"}; 
        return  new MyDefaultTableModel(o,col);      
    }
    private ArrayList<History> toArrayList(ResultSet rs){
        try{
            ArrayList<History> al = new ArrayList<History>();
            History hs = null;
            while(rs.next()){
                hs = new History();
                hs.setExitTime(rs.getDate("exitTime"));
                hs.setId(rs.getInt("id"));
                hs.setMoney(rs.getFloat("money"));
                hs.setName(rs.getString("name"));
                hs.setRegisterTime(rs.getDate("registerTime"));
                hs.setRemarks(rs.getString("remarks"));
                hs.setRoom(rs.getInt("room"));
                al.add(hs);
            }
            rs.close();
            return al;
        }catch(SQLException ex){
            System.out.print(ex.getMessage());
            return null;
        }   
    }
    
    private ResultSet getHistorys() throws SQLException, ClassNotFoundException{
        sql = "SELECT * FROM history ";
        PreparedStatement ps = getPreparedStatement(sql);
        return ps.executeQuery();
    }
    
    private ResultSet getNoHistorys() throws SQLException, ClassNotFoundException {
        sql = "SELECT * FROM history WHERE remarks=?";
        PreparedStatement ps = getPreparedStatement(sql);
            ps.setString(1,"欠账");
        return ps.executeQuery();
    }
    //4.还账处理
    public boolean pay(String name) throws ClassNotFoundException{
        sql = "UPDATE history SET remarks = ? WHERE name = ?";
        try{
            PreparedStatement ps = getPreparedStatement(sql);
                ps.setString(1, "已清账");
                ps.setString(2, name);
                ps.executeUpdate();
                ps.close();
                return true;
        }catch (SQLException e){
            return false;
        }
    }                    
}                  