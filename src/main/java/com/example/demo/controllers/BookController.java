package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.Response;
import com.example.demo.repositories.BookDaoDB;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/BookDBService")
public class BookController {

    private static BookDaoDB bookDao = new BookDaoDB();



    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookDao.getAllBooks();
    }


    @GetMapping("/booksJSON")
    public List<Book> getBooksJSON(){
        return bookDao.getAllBooks();
    }

    @GetMapping("/booksHTML")
    public String getBooksHTML(){
        String res = "<HTML><HEAD><TITLE>Books</TITLE></HEAD><BODY><TABLE>";
        for (Book b : bookDao.getAllBooks()){
            res += "<TR><TD>"+b.getId()+"</TD><TD>"+b.getAuthor()+"</TD><TD>"+b.getTitle()+"</TD></TR>";
        }
        res += "</TABLE></HTML>";
        return res;
    }


     @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable("id") int id){
        Book res = new Book();
        for (Book b : bookDao.getAllBooks()){
            if (b.getId() == id){
                res = b;
            }
        }
        return res;
    }


    @GetMapping("/book/{id}/delete")
    public Response deleteBookById(@PathVariable("id") int id){
        Response res = new Response("Book deleted", bookDao.deleteBook(id));
        return res;
    }

    @PostMapping("/book/add")
    public Response addBook(Book b){
        Response res = new Response("Book added", bookDao.addBook(b));
        return res;
    }


    @PostMapping("/book/update")
    public Response upsertBook(Book b){
        Response res = new Response("Book updated", bookDao.updateBook(b));
        return res;
    }

}