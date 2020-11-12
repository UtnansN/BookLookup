package com.utnans.books.controller;

import com.utnans.books.dto.BookDTO;
import com.utnans.books.entity.Book;
import com.utnans.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller("/")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String getBookPage(Model model, @RequestParam Map<String, String> params) {
        List<Book> bookList = bookService.getBooks(params);
        model.addAttribute("books", bookList);
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
