package com.utnans.books.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany
    @JoinTable(name="author_books",
            joinColumns = {@JoinColumn(name = "fk_author")},
            inverseJoinColumns = {@JoinColumn(name = "fk_book")})
    private List<Book> books;

}
