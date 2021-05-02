package com.example.demo.models;


import java.io.Serializable;
import java.util.Date;


public class Book  implements Serializable{
     private String title;
    private String author;
    private Date read;
    private int id;

    public Book(){}

    public Book(int id, String title, String author){
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Book(int id, String title, String author, Date read){
        this.id = id;
        this.title = title;
        this.author = author;
        this.read = read;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getRead() {
        return read;
    }

    public void setRead(Date read) {
        this.read = read;
    }
}
