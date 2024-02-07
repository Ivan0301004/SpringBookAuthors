package com.spring.booksauthors.service.impl;

import com.spring.booksauthors.exceptions.ResourceNotFoundException;
import com.spring.booksauthors.model.Author;
import com.spring.booksauthors.model.Book;
import com.spring.booksauthors.repository.AuthorRepository;
import com.spring.booksauthors.repository.BookRepository;
import com.spring.booksauthors.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.spring.booksauthors.utils.ResponseUtil.resourceUri;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> getAllBookByAuthorId(long authorId, Pageable pageable) {
        return bookRepository.findByAuthorId(authorId, pageable);
    }

    @Override
    public ResponseEntity<Book> createBook(long authorId, Book bookR) {
        return authorRepository.findById(authorId)
                .map(author -> {
                    bookR.setAuthor(author);
                    return bookRepository.save(bookR);
                })
                .map(book -> ResponseEntity.created(resourceUri(book.getId())).body(book))
                .orElseThrow(() -> new ResourceNotFoundException("AuthorID " + authorId + " not found!"));
    }

    @Override
    public ResponseEntity<Book> createNewBook(Book book) {
        return Optional.of(book)
                .map(bookRepository::save)
                .map(book1 ->
                        ResponseEntity.created(resourceUri(book1.getId())).body(book1))
                .orElseThrow(IllegalArgumentException::new);
    }

    public ResponseEntity<Book> addExistingBookToAnAuthor(long authorId, long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + bookId + " not found!"));

        return authorRepository.findById(authorId)
                .map(author -> {
                    author.getBooks().add(book);
                    book.setAuthor(author);
                    authorRepository.save(author);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ResponseEntity<Book> updateBook(long authorId, long bookId, Book bookR) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + authorId + " was not found"));

        return bookRepository.findById(bookId)
                .map(book -> {
                    book.setTitle(bookR.getTitle());
                    book.setIsbn(bookR.getIsbn());
                    book.setPublishedDate(bookR.getPublishedDate());
                    book.setAuthor(author);
                    book.setPageCount(bookR.getPageCount());
                    return book;
                })
                .map(book -> ResponseEntity.ok().location(resourceUri(book.getId())).body(book))
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + bookId + " was not found!"));
    }

    @Override
    public ResponseEntity<?> deleteBook(long authorId, long bookId) {
        return bookRepository.findByIdAndAuthorId(bookId, authorId)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId + " and author " +
                        "with ID " + authorId));
    }

    @Override
    public ResponseEntity<?> deleteBookFromAuthor(long authorId, long bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + authorId + " not found!"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + bookId + " not found!"));

        if (author.getBooks().contains(book)) {
            author.getBooks().remove(book);
            book.setAuthor(null);
            authorRepository.save(author);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } else {
            throw new IllegalStateException("Book with id " + bookId + " is not associated with Author with id " + authorId);
        }
    }

}
