package com.utnans.books.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(name="author_books",
            joinColumns = {@JoinColumn(name = "fk_author")},
            inverseJoinColumns = {@JoinColumn(name = "fk_book")})
    private List<Book> books;
}
