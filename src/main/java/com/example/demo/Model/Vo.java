package com.example.demo.Model;

public class Vo implements java.io.Serializable{

    private int id;
    private String EnglishName;
    private String ChineseName;

    public Vo(int id, String englishName, String chineseName) {
        this.id = id;
        EnglishName = englishName;
        ChineseName = chineseName;
    }

    public Vo(){

    }


    @Override
    public String toString() {
        return "Vo{" +
                "id=" + id +
                ", EnglishName='" + EnglishName + '\'' +
                ", ChineseName='" + ChineseName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getChineseName() {
        return ChineseName;
    }

    public void setChineseName(String chineseName) {
        ChineseName = chineseName;
    }
}
