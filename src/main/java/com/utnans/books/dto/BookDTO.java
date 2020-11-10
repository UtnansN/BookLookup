package com.utnans.books.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDTO {

    private String title;

    private Integer publishingYear;

    private String publisher;

    private List<String> authors;
}
