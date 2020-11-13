package com.utnans.books.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @Column(name = "year")
    private Integer publishingYear;

    @ManyToOne
    @JoinColumn(name = "fk_publisher")
    private Publisher publisher;

    @ManyToMany(mappedBy = "books")
    @OrderBy("name")
    private List<Author> authors;
}
