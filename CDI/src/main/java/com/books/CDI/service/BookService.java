package com.books.CDI.service;

import com.books.CDI.exception.ResourceNotFoundException;
import com.books.CDI.model.Book;
import com.books.CDI.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Récupérer tous les livres
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Récupérer un livre par ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Ajouter un nouveau livre
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Mettre à jour un livre
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsBorrowed(bookDetails.isBorrowed());
        return bookRepository.save(book);
    }

    // Supprimer un livre
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookRepository.delete(book);
    }
}
