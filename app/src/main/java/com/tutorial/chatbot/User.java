package com.tutorial.chatbot;

public class User {
    String name;
    String email;
    String profilePic;

    public User()
    {

    }

    public User(String name,String email,String profilePic)
    {
        this.name=name;
        this.email=email;
        this.profilePic=profilePic;

    }

    public void setModel()
    {
        this.name=name;
    }

    public String getModel()
    {
        return name;
    }

    public void setPrice()
    {
        this.email=email;
    }

    public String getPrice()
    {
        return email;
    }
}
