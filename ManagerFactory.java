/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

/**
 *为4个实体类创建工厂类,以便于新建对象实例,调用方法
 * @author Administrator
 */

public class ManagerFactory {
    protected ManagerFactory(){}//空构造函数
    
    public static ManagerFactory newInstance (){ //创建新实例
        return new ManagerFactory();        
    }
    public AdminManager getAdminManager(){
        return new AdminManager();
    }
    public RoomManager getRoomManager(){
        return new RoomManager();
    }
    public CustomerManager getCustomerManager(){
        return new CustomerManager();
    }
    public HistoryManager getHistoryManager(){
        return new HistoryManager();
    }
}
