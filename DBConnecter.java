/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *数据库操作实体类
 * @author Administrator
 */
public class DBConnecter {
    private String driver ="com.mysql.jdbc.Driver";  //MySQL驱动
    private String url = "jdbc:mysql://localhost:3306/hotel";//数据库URL
    
    public Connection getConnection() throws SQLException, ClassNotFoundException{
        try{
            Class.forName(getDriver());      //调用getDriver（）方法加载驱动
        }catch(java.lang.ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(getUrl(),"root","WEINAI");//获取数据库连接
        return conn;
    }
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
