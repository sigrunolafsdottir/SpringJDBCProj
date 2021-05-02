package com.example.demo.repositories;



import com.example.demo.models.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BookDaoDB {

    public BookDaoDB ()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public List<Book> getAllBooks(){
        List<Book> allBooks = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books",
                "sigrun","secretpassword");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from book")){

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Date read = rs.getDate("read");
                allBooks.add(new Book(id, title, author, read));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allBooks;
    }


    public boolean deleteBook(int id){
        int rowChanged = 0;
        String query = "delete from Book where id = ? ";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books",
                "sigrun","secretpassword");
             PreparedStatement stmt = con.prepareStatement(query)){

            stmt.setInt(1, id);
            System.out.println("deleted book "+ id);
            rowChanged = stmt.executeUpdate();
            if (rowChanged == 1){  //en rad ändrades
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBook(Book b){
        String query = "insert into Book (title, author) values (?, ?)";
        int rowChanged = 0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books",
                "sigrun","secretpassword");
             PreparedStatement stmt = con.prepareStatement(query)){

            stmt.setString(1, b.getTitle());
            stmt.setString(2, b.getAuthor());
            rowChanged = stmt.executeUpdate();
            if (rowChanged == 1){  //en rad ändrades
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBook(Book b){

        String queryUpdate = "UPDATE Book SET title = ?, author = ? where id = ? ;";
        String queryInsert = "insert into Book (title, author) values (?, ?)";
        int rowChanged = -1;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books",
                "sigrun","secretpassword");
             PreparedStatement stmtUpdate = con.prepareStatement(queryUpdate);
             PreparedStatement stmtInsert = con.prepareStatement(queryInsert)){

            stmtUpdate.setString(1, b.getTitle());
            stmtUpdate.setString(2, b.getAuthor());
            stmtUpdate.setInt(3, b.getId());
            rowChanged = stmtUpdate.executeUpdate();
            System.out.println("i updateBook, rowChanged "+ rowChanged);

            //det fanns ingen rad att uppdatera, vi gör en insert
            if (rowChanged == 0){
                stmtInsert.setString(1, b.getTitle());
                stmtInsert.setString(2, b.getAuthor());
                rowChanged = stmtInsert.executeUpdate();
            }
            if (rowChanged == 1){  //en rad ändrades
                return true;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}