package com.spring.booksauthors.repository;

import com.spring.booksauthors.model.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT u FROM Author u LEFT JOIN u.books b")
    List<Author> findAllWithBooks(Pageable pageable);
}
