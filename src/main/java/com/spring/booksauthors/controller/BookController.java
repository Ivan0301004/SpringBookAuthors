package com.spring.booksauthors.controller;

import com.spring.booksauthors.model.Book;
import com.spring.booksauthors.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Page<Book> getAlBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }


    @GetMapping("/authors/{authorId}/books")
    public Page<Book> getAllBookByAuthorId(@PathVariable long authorId, Pageable pageable) {
        return bookService.getAllBookByAuthorId(authorId, pageable);
    }

    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<Book> createBook(@PathVariable long authorId, @RequestBody Book bookR) {
        return bookService.createBook(authorId, bookR);
    }

    @PostMapping("/authors/books")
    public ResponseEntity<Book> createNewBook(@RequestBody Book book) {
        return bookService.createNewBook(book);
    }

    @PostMapping("/authors/{authorId}/books/{bookId}")
    public ResponseEntity<Book> addExistingBookToAnAuthor(@PathVariable long authorId, @PathVariable long bookId) {
        return bookService.addExistingBookToAnAuthor(authorId, bookId);
    }

    @PutMapping("/authors/{authorId}/books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable long authorId, @PathVariable long bookId,
                                           @RequestBody Book bookR) {
        return bookService.updateBook(authorId, bookId, bookR);
    }

    @DeleteMapping("/authors/{authorId}/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable long authorId, @PathVariable long bookId) {
        return bookService.deleteBook(authorId, bookId);
    }

    @DeleteMapping("delete/authors/{authorId}/books/{bookId}")
    public ResponseEntity<?> deleteBookFromAuthor(@PathVariable long authorId, @PathVariable long bookId) {
        return bookService.deleteBookFromAuthor(authorId, bookId);
    }


}
