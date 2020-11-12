package com.utnans.books.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private String title;

    private Integer publishingYear;

    private String publisher;

    private String authors;
}
