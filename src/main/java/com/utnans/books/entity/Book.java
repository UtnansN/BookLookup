package com.utnans.books.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private Integer publishingYear;

    private String publisher;

    @ManyToMany(mappedBy = "books")
    @OrderBy("lastName, firstName")
    private List<Author> authors;
}
