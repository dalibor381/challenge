package com.ama.agencybooks.service;

import com.ama.agencybooks.model.Book;
import com.ama.agencybooks.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ama.agencybooks.model.SecurityLevel.UNCLASSIFIED;

@Service
public class BookService {
    
    private BookRepository bookRepo;
    
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> getUnclassifiedBooks() {
        return bookRepo.findAll()
                .stream()
                .filter(n -> n.getClassification().equals(UNCLASSIFIED))
                .collect(Collectors.toList());
    }

    public Optional<Book> getBook(Long bookId) {
            return bookRepo.findById(bookId);
        }

}