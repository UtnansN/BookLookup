package com.utnans.books.controller;

import com.utnans.books.dto.BookDTO;
import com.utnans.books.dto.SearchDTO;
import com.utnans.books.entity.Book;
import com.utnans.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller("/")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String getBookPage(Model model, @ModelAttribute SearchDTO searchDTO, Pageable pageable) {
        if (searchDTO == null) searchDTO = new SearchDTO();

        HashMap<String, String> params = new HashMap<>();
        params.put("query", searchDTO.getQuery());
        params.put("criteria", searchDTO.getCriteria());

        Page<Book> bookPage = bookService.getBooks(params, pageable);
        model.addAttribute("books", bookPage);
        model.addAttribute("searchDTO", searchDTO);
        return "index";
    }

    @GetMapping("/book/add")
    public String getCreatePage(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        return "add-book";
    }

    @GetMapping("/book/{id}")
    public String getBookByID(Model model, @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @PostMapping("/book")
    public String addBook(@ModelAttribute BookDTO bookDTO) {
        Book book = bookService.addBook(bookDTO);
        return "redirect:/book/" + book.getId();
    }

    @PostMapping("/book/{id}/author")
    public String addAuthor(@PathVariable Long id, @RequestParam String authorName) {
        bookService.addAuthorToExistingBook(id, authorName);
        return "redirect:/book/" + id;
    }
}
