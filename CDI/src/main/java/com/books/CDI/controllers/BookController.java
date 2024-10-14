package com.books.CDI.controllers;

import com.books.CDI.exception.ResourceNotFoundException;
import com.books.CDI.model.Book;
import com.books.CDI.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Récupérer tous les livres
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Récupérer un livre par ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long bookId) {
        Book book = bookService.getBookById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        return ResponseEntity.ok().body(book);
    }

    // Ajouter un nouveau livre
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    // Mettre à jour un livre
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId,
                                           @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(bookId, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    // Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable(value = "id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
