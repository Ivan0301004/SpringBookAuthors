package com.spring.booksauthors.service;

import com.spring.booksauthors.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthorService {

    Page<Author> getAllAuthors(Pageable pageable);

    ResponseEntity<?> getAuthorById(long id);

    ResponseEntity<Author> saveAuthor(Author authorR);

    ResponseEntity<Author> updateAuthor(final long authorId, Author authorR);

    ResponseEntity<?> deleteAuthor(long authorId);
}
