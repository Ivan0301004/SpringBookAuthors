package com.spring.booksauthors.service;

import com.spring.booksauthors.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BookService {

    Page<Book> getAllBooks(Pageable pageable);

    Page<Book> getAllBookByAuthorId(long authorId, Pageable pageable);

    ResponseEntity<Book> createBook(long authorId, Book bookR);

    ResponseEntity<Book> createNewBook(Book book);

    ResponseEntity<Book> addExistingBookToAnAuthor(long authorId, long bookId);

    ResponseEntity<Book> updateBook(long authorId, long bookId, Book bookR);

    ResponseEntity<?> deleteBook(long authorId, long bookId);

    ResponseEntity<?> deleteBookFromAuthor(long authorId, long bookId);


}
