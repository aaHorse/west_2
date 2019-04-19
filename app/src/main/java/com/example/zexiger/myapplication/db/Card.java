package com.example.zexiger.myapplication.db;

import java.util.List;

public class Card {

    /**
     * log_id : 7660988584488828307
     * direction : 0
     * words_result_num : 12
     * words_result : [{"words":"学生卡"},{"words":"黎家泽"},{"words":"学号:221701414"},{"words":"学院:软件学院"},{"words":"注意事项"},{"words":"1本卡解释权归福州大学所有。"},{"words":"2.卡片避免磨损、弯折、油污和静电"},{"words":"3本卡一旦涂改或剪角则立即作废"},{"words":"4请关注微信服务号\u201c福州大学一卡"},{"words":"5校园卡中心电话"},{"words":":0591-2286531"},{"words":"6网上办事大厅:http://ehall.fzu"}]
     */

    private long log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * words : 学生卡
         */

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
