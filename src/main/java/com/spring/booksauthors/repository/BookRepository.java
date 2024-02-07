package com.spring.booksauthors.repository;

import com.spring.booksauthors.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByAuthorId(long authorId, Pageable pageable);

    Optional<Book> findByIdAndAuthorId(long bookId, long authorId);

    @Query("SELECT b, a.id, a.name, a.age FROM Book b JOIN b.author a")
    List<Object[]> findBooksWithAuthors();
}
