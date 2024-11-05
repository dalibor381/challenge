package com.ama.agencybooks.service;

import com.ama.agencybooks.model.Book;
import com.ama.agencybooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ama.agencybooks.model.SecurityLevel.UNCLASSIFIED;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;
    
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Optional<Book> getBook(Long bookId) {
        return bookRepo.findById(bookId);
    }

    public List<Book> getUnclassifiedBooks() {
        return bookRepo.findByClassification(UNCLASSIFIED);
    }

}