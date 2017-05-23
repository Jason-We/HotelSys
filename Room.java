/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

/**
 * 房间实体类，对应数据库中的room表
 * @author Administrator
 */
public class Room {
    private int rmid;//编号
    private String grade; //级别
    private float price; //价格
    private int state; //状态
    private String remarks;//备注

    public int getId() {
        return rmid;
    }

    public void setId(int id) {
        this.rmid = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
