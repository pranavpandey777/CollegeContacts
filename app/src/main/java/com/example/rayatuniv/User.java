package com.example.rayatuniv;

public class User {

    String name;
    String email;
    String num;
    String college;
    String download;
    String type;
    String id;
    String dept;
    String uid;

    public User(String name, String email, String num, String college, String download, String type, String id, String dept,String uid) {
        this.name = name;
        this.email = email;
        this.num = num;
        this.college = college;
        this.download = download;
        this.type = type;
        this.id = id;
        this.dept = dept;
        this.uid=uid;
    }

    public User() {

    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNum() {
        return num;
    }

    public String getCollege() {
        return college;
    }

    public String getDownload() {
        return download;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getDept() {
        return dept;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
