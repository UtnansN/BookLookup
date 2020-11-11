package com.utnans.books.service;

import com.utnans.books.dto.BookDTO;
import com.utnans.books.entity.Author;
import com.utnans.books.entity.Book;
import com.utnans.books.repository.AuthorRepository;
import com.utnans.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooks(Map<String, String> params) {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Book addBook(BookDTO bookDTO) {
        Book book = new Book();
        // I would usually use a mapper class here but the method's short anyways
        book.setTitle(bookDTO.getTitle());
        book.setPublishingYear(bookDTO.getPublishingYear());
        book.setPublisher(bookDTO.getPublisher());
        bookRepository.save(book);

        List<Author> authors = Arrays.stream(bookDTO.getAuthors().split(","))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .distinct()
                .map(this::createOrFetchAuthorObject)
                .collect(Collectors.toList());

        authors.forEach(author -> author.getBooks().add(book));
        authorRepository.saveAll(authors);
        return book;
    }

    public void addAuthorToExistingBook(Long bookID, String authorName) {
        authorName = authorName.trim();
        if (authorName.isEmpty()) return;

        Book book = bookRepository.findById(bookID).orElseThrow(EntityNotFoundException::new);
        Author author = createOrFetchAuthorObject(authorName);
        if (book.getAuthors().stream().anyMatch(ba -> ba.getId().equals(author.getId()))) return;
        author.getBooks().add(book);
        authorRepository.save(author);
    }

    private Author createOrFetchAuthorObject(String name) {
        Optional<Author> author = authorRepository.findAuthorByName(name);

        if (author.isPresent()) {
            // If author is found, then the book is updated with a reference to that author
            return author.get();
        } else {
            // If author with name is not found, create a new author object
            Author newAuthor = new Author();
            newAuthor.setName(name);
            newAuthor.setBooks(new ArrayList<>());
            return newAuthor;
        }
    }
}
