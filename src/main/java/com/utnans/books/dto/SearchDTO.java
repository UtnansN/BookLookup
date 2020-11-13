package com.utnans.books.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchDTO {

    private String query = "";

    private String criteria = "";
}
