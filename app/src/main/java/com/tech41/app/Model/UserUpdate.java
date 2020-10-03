package com.tech41.app.Model;

public class UserUpdate {

    private String id ;
    private int column ;
    private String value ;

    public UserUpdate(String id, int column, String value) {
        this.id = id;
        this.column = column;
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
