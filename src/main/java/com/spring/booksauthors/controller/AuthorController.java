package com.spring.booksauthors.controller;

import com.spring.booksauthors.model.Author;
import com.spring.booksauthors.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/authors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorService.getAllAuthors(pageable);
    }

    @GetMapping("/authors/{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable(name = "authorId") long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author authorR) {
        return authorService.saveAuthor(authorR);
    }

    @PutMapping("/authors/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable final long authorId, @RequestBody Author authorR) {
        return authorService.updateAuthor(authorId, authorR);
    }


    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable long authorId) {
        return authorService.deleteAuthor(authorId);
    }
}
