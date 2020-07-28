package com.icdominguez.duckhunt.models;

public class User {
    private String username;
    private int ducks;


    public User() {

    }

    public User(String username, int ducks) {
        this.username = username;
        this.ducks = ducks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDucks() {
        return ducks;
    }

    public void setDucks(int ducks) {
        this.ducks = ducks;
    }
}
