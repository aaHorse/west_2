package com.example.zexiger.myapplication.entity;

public class Data {

    /**
     * id : 1
     * name : 上帝的骰子
     * address : 再能掷出七点的地方
     * info : 上帝从来不会掷的骰子
     * type : 橙色装备
     * image : http://192.168.43.61:8080/img/1.png
     * date : 0000-0-0
     * phone : 123456
     * isfound : 1
     * isexist : 1
     */

    private String name;
    private String address;
    private String info;
    private String type;
    private String image;
    private String date;
    private String phone;
    private int isfound;
    private int isexist;
    /*
     * QQ昵称和头像地址
     * */
    private String qq_name;
    private String qq_image;
    private int visits;


    /*
    * 把id去掉，用时间戳作为唯一标识
    * */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsfound() {
        return isfound;
    }

    public void setIsfound(int isfound) {
        this.isfound = isfound;
    }

    public int getIsexist() {
        return isexist;
    }

    public void setIsexist(int isexist) {
        this.isexist = isexist;
    }

    public String getQq_name() {
        return qq_name;
    }

    public void setQq_name(String qq_name) {
        this.qq_name = qq_name;
    }

    public String getQq_image() {
        return qq_image;
    }

    public void setQq_image(String qq_image) {
        this.qq_image = qq_image;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
}
