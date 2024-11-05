package com.ama.agencybooks.controller;

import com.ama.agencybooks.model.Book;
import com.ama.agencybooks.repository.BookRepository;
import com.ama.agencybooks.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BooksController {

    private BookService bookService;
    private BookRepository bookRepo;

    public BooksController(BookService bookService, BookRepository bookRepo) {
        this.bookService = bookService;
        this.bookRepo = bookRepo;
    }

    @GetMapping("/public/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    @GetMapping("/books/{bookId}")
    public Optional<Book> getBook(@RequestParam Long bookId) {
        return bookService.getBook(bookId);
    }
    
    @GetMapping("/books/topSecret")
    public List<Book> getSecretBooksList() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/unclassified")
    public List<Book> getUnclassifiedBooks() {
        return bookService.getUnclassifiedBooks();
    }
    
    @PostMapping("/public/addBook")
    public Book addBook(@RequestBody Book newBook) {
        return bookRepo.save(newBook);
    }
}
