package com.VirtualBookStore.service;

import com.VirtualBookStore.dao.BookRepository;
import com.VirtualBookStore.dto.Book;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private  BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) {
        Optional<Book> optionalBook  = bookRepository.findById((long) id);
        return optionalBook.orElse(null);
    }

    @Override
    public Book updateBook(int id, Book book) {
        Book existingBook=bookRepository.findById((long) id).get();
        existingBook.setTitle(book.getTitle());
        existingBook.setPrice(book.getPrice());
        existingBook.setStock(book.getStock());
        existingBook.setAvgRating(book.getAvgRating());
        Book updatedBook=bookRepository.save(existingBook);

        return updatedBook;
    }

    @Override
    public long deleteBookById(int id) {
        if(bookRepository.existsById((long) id)){
            bookRepository.deleteById((long) id);
            return 1;
        }
        return 0;
    }
    @Override
    public boolean checkStock(Long bookId, int quantity) {
        Book book = bookRepository.findById((long) Math.toIntExact(bookId))
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return book.getStock() != null && book.getStock() >= quantity;
    }

    //  Reduce stock after successful order
    @Override
    public void reduceStock(Long bookId, int quantity) {
        Book book = bookRepository.findById((long) Math.toIntExact(bookId))
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStock() == null || book.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
        }

        book.setStock(book.getStock() - quantity);
        bookRepository.save(book);
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.findByTitleOrAuthor(keyword);
    }


}