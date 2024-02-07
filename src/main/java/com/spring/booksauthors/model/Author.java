package com.spring.booksauthors.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.util.digester.ArrayStack;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private long id;

    private String name;

    private int age;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "author",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = false)
    private List<Book> books = new ArrayList<>();

}
