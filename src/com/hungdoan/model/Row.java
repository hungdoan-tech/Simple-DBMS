package com.hungdoan.model;

import static com.hungdoan.common.Constant.COLUMN_EMAIL_SIZE;
import static com.hungdoan.common.Constant.COLUMN_USERNAME_SIZE;

public class Row {

    private int id;

    private char[] username = new char[COLUMN_USERNAME_SIZE];

    private char[] email = new char[COLUMN_EMAIL_SIZE];

    public Row() {

    }

    public Row(int id, char[] username, char[] email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char[] getUsername() {
        return username;
    }

    public void setUsername(char[] username) {
        this.username = username;
    }

    public char[] getEmail() {
        return email;
    }

    public void setEmail(char[] email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", username=" + new String(username) +
                ", email=" + new String(email) +
                '}';
    }
}
