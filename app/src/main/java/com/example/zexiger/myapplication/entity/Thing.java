package com.example.zexiger.myapplication.entity;

import java.util.List;

public class Thing {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * name : 上帝的骰子
         * adress : 再能掷出七点的地方
         * info : 上帝从来不会掷的骰子
         * type : 橙色装备
         * image : http://192.168.43.61:8080/img/1.png
         * date : 0000-0-0
         * phone : 123456
         * isfound : 1
         * isexist : 1
         */

        private int id;
        private String name;
        private String adress;
        private String info;
        private String type;
        private String image;
        private String date;
        private String phone;
        private int isfound;
        private int isexist;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
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
    }
}
