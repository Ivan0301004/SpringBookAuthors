package com.spring.booksauthors.service.impl;

import com.spring.booksauthors.exceptions.ResourceNotFoundException;
import com.spring.booksauthors.model.Author;
import com.spring.booksauthors.repository.AuthorRepository;
import com.spring.booksauthors.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.spring.booksauthors.utils.ResponseUtil.resourceUri;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> getAuthorById(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found!"));
        return ResponseEntity.ok().body(author);
    }

    @Override
    public ResponseEntity<Author> saveAuthor(Author authorR) {
        return Optional.of(authorR)
                .map(authorRepository::save)
                .map(author ->
                        ResponseEntity.created(resourceUri(author.getName())).body(author))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ResponseEntity<Author> updateAuthor(long authorId, Author authorR) {
        return authorRepository.findById(authorId)
                .map(author -> {
                    author.setName(authorR.getName());
                    author.setAge(authorR.getAge());
                    author.setBirthDate(authorR.getBirthDate());
                    return author;
                })
                .map(authorRepository::save)
                .map(author -> ResponseEntity.ok().location(resourceUri(authorId)).body(author))
                .orElseThrow(() -> new ResourceNotFoundException("AuthorID " + authorId + " not found."));
    }

    @Override
    public ResponseEntity<?> deleteAuthor(long authorId) {
        return authorRepository.findById(authorId)
                .map(author -> {
                    authorRepository.delete(author);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("AuthorID " + authorId + " not found."));
    }
}
