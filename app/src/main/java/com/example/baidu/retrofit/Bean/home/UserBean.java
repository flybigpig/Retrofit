package com.example.baidu.retrofit.Bean.home;

import java.util.ArrayList;
import java.util.List;

public class UserBean {

    private boolean admin;
    private List<String> chapterTops = new ArrayList<>();
    private List<String> collectIds = new ArrayList<>();
    private String email;
    private String icon;
    private int id;
    private String nickname;
    private String password;
    private String publicName;
    private String token;
    private int type;
    private String username;


    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }


    public void setChaptertops(List<String> chaptertops) {
        this.chapterTops = chaptertops;
    }

    public List<String> getChaptertops() {
        return chapterTops;
    }


    public void setCollectids(List<String> collectids) {
        this.collectIds = collectids;
    }

    public List<String> getCollectids() {
        return collectIds;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public void setPublicname(String publicname) {
        this.publicName = publicname;
    }

    public String getPublicname() {
        return publicName;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
