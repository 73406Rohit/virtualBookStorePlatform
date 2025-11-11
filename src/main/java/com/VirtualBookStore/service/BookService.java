package com.VirtualBookStore.service;

import com.VirtualBookStore.dto.Book;

import java.util.List;

public interface BookService {

    public Book addBook(Book book);
    public List<Book> getAllBooks();
    public Book findById(int id);
    public Book updateBook(int id,Book book);
    public long deleteBookById(int id);
    boolean checkStock(Long bookId, int quantity);
    void reduceStock(Long bookId, int quantity);
    List<Book> searchBooks(String keyword);





}
