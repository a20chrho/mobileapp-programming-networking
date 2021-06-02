package com.example.networking;

import com.google.gson.annotations.SerializedName;

public class Mountains {

    @SerializedName("ID")
    private String id;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;
    @SerializedName("size")
    private int meters;
    @SerializedName("cost")
    private int feet;
    private Auxdata auxdata;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public int getMeters() {
        return meters;
    }

    public int getFeet() {
        return feet;
    }

    public Auxdata getAuxdata() {
        return auxdata;
    }

    @Override
    //toString = Everything put into a string
    public String toString() { return name; }

    public String info()
    {
        String tmp=new String();
        tmp+=name+" is located in mountain tange "+location+" and reaches "+meters+"m above sea level.";
        return tmp;
    }

}