package com.utnans.books.service;

import com.utnans.books.dto.BookDTO;
import com.utnans.books.entity.Author;
import com.utnans.books.entity.Book;
import com.utnans.books.entity.Publisher;
import com.utnans.books.repository.AuthorRepository;
import com.utnans.books.repository.BookRepository;
import com.utnans.books.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PublisherRepository publisherRepository;

    // Used the Example API for this because I did not know it before and it looked interesting.
    public Page<Book> getBooks(Map<String, String> params, Pageable pageable) {
        if (params.containsKey("query") && !params.get("query").trim().isEmpty()) {
            String query = params.get("query").trim();
            String criteria = params.get("criteria");

            Book book = new Book();
            switch(criteria) {
                case "title":
                    book.setTitle(query);
                    break;
                case "year":
                    try {
                        book.setPublishingYear(Integer.parseInt(query));
                    } catch (NumberFormatException e) {
                        return Page.empty();
                    }
                    break;
                case "publisher":
                    Publisher publisher = new Publisher();
                    publisher.setName(query);
                    book.setPublisher(publisher);
                    break;
                case "author":
                    return bookRepository.findDistinctByAuthorsNameContaining(query, pageable);
                default:
                    return bookRepository.findAll(pageable);
            }

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase();

            Example<Book> bookExample = Example.of(book, matcher);
            return bookRepository.findAll(bookExample, pageable);
        }
        else {
            return bookRepository.findAll(pageable);
        }
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Book addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setPublishingYear(bookDTO.getPublishingYear());

        Publisher publisher = new Publisher();
        publisher.setName(bookDTO.getPublisher());
        Optional<Publisher> candidate = publisherRepository.findOne(Example.of(publisher));

        if (candidate.isPresent()) {
            publisher = candidate.get();
        } else {
            publisherRepository.save(publisher);
        }

        book.setPublisher(publisher);
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
