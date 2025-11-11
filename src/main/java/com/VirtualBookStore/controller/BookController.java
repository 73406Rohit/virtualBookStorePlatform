package com.VirtualBookStore.controller;

import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
   private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    // add new Book
    @PostMapping(value = "/addbook",consumes = "application/json",produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        System.out.println(book);
        Book savedBook =bookService.addBook(book);
        return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
    }

    // Get all books
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books=bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    @GetMapping("/searchbooks")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String q) {
        List<Book> books = bookService.searchBooks(q);
        return ResponseEntity.ok(books);
    }


    // find books by id
    @GetMapping("/findBookById/{id}")
    public ResponseEntity<Book> findById(@PathVariable int id){
        Book book=bookService.findById(id);
        if(book !=null) {
            return ResponseEntity.ok(book);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // update book by id
    @PutMapping("/updateBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book>update (@PathVariable int id,@RequestBody Book book){
        Book updatedBook=bookService.updateBook(id,book);
        return new ResponseEntity<Book>(updatedBook,HttpStatus.OK);
    }

    // delete book by id
    @DeleteMapping("/deleteBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable int id){
        long deleteCoubnt =bookService.deleteBookById(id);
        Map<String,Object> response =new HashMap<>();
        response.put("deletecount",deleteCoubnt);
        if(deleteCoubnt >0){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

            }
        }

}
