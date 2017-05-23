/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

/**
 *系统主类
 * 包含初始化登录窗口
 * @author Administrator
 */
public class HotelSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run(){
                Login dialog = new Login();             
                dialog.setVisible(true);
            }
        });
    }
    
}
