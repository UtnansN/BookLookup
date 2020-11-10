package com.utnans.books.service;

import com.utnans.books.dto.BookDTO;
import com.utnans.books.entity.Author;
import com.utnans.books.entity.Book;
import com.utnans.books.repository.AuthorRepository;
import com.utnans.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return book;
    }

    public void addAuthor(Long bookID, String authorName) {
        Book book = bookRepository.findById(bookID).orElseThrow(EntityNotFoundException::new);
        String[] splitName = authorName.split(" ");

        String firstName, lastName;
        if (splitName.length == 0) return;
        lastName = splitName[splitName.length-1];

        // Middle names ignored for now
        Optional<Author> author;
        if (splitName.length == 1) {
            firstName = "";
            author = authorRepository.findAuthorByLastName(lastName);
        } else {
            firstName = splitName[0];
            author = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
        }

        if (author.isPresent()) {
            // If author is found, then the book is updated with a reference to that author
            book.getAuthors().add(author.get());
        } else {
            // If author with name is not found, add a new author and add the book to the author's published books
            Author newAuthor = new Author();
            newAuthor.setFirstName(firstName);
            newAuthor.setLastName(lastName);
            newAuthor.setBooks(List.of(book));
            authorRepository.save(newAuthor);
        }
    }
}
