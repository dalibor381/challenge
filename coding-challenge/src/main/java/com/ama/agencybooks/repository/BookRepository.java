package com.ama.agencybooks.repository;

import com.ama.agencybooks.model.Book;
import com.ama.agencybooks.model.SecurityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    @Query("select b from books b where b.classification = ?1")
    List<Book> findByClassification(SecurityLevel classification);
}
