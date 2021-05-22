package com.example.networking;

public class Mountain {
    // State goes here
    private String name;
    private String location;
    private int height;

    // Interface

    // Default Constructor
    public Mountain()
    {
        name="Saknar namn";
        location="Saknar plats";
        height=-1;
    }

    // Constructor that take parameters to create a new Mountain
    public Mountain(String n,String l,int h)
    {
        name=n;
        location=l;
        height=h;
    }

    public String info()
    {
        String tmp=new String();
        tmp+=name+" is located in mountain tange "+location+" and reaches "+height+"m above sea level.";
        return tmp;
    }

    public void setName(String n)
    {
        name=n;
    }
    public String getName()
    {
        return name;
    }

    // Add more methods to your Mountain class if you need them...
}
