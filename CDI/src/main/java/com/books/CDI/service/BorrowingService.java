package com.books.CDI.service;

import com.books.CDI.exception.ResourceNotFoundException;
import com.books.CDI.model.Book;
import com.books.CDI.model.Borrowing;
import com.books.CDI.model.User;
import com.books.CDI.repository.BorrowingRepository;
import com.books.CDI.repository.BookRepository;
import com.books.CDI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Emprunte un livre pour un utilisateur.
     *
     * @param userId l'ID de l'utilisateur
     * @param bookId l'ID du livre
     * @return l'emprunt créé
     * @throws ResourceNotFoundException si l'utilisateur ou le livre n'est pas trouvé
     * @throws IllegalStateException     si le livre est déjà emprunté
     */
    @Transactional
    public Borrowing borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        // Mettre à jour le statut du livre
        book.setIsBorrowed(true);
        bookRepository.save(book);

        // Créer un nouvel emprunt
        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowedAt(LocalDateTime.now());
        borrowing.setReturnedAt(null);

        return borrowingRepository.save(borrowing);
    }

    /**
     * Retourne un livre emprunté.
     *
     * @param borrowingId l'ID de l'emprunt
     * @return l'emprunt mis à jour
     * @throws ResourceNotFoundException si l'emprunt n'est pas trouvé
     * @throws IllegalStateException     si le livre est déjà retourné
     */
    @Transactional
    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing", "id", borrowingId));

        if (borrowing.getReturnedAt() != null) {
            throw new IllegalStateException("Book has already been returned.");
        }

        // Mettre à jour le statut du livre
        Book book = borrowing.getBook();
        book.setIsBorrowed(false);
        bookRepository.save(book);

        // Mettre à jour l'emprunt
        borrowing.setReturnedAt(LocalDateTime.now());
        return borrowingRepository.save(borrowing);
    }

    /**
     * Récupère tous les emprunts d'un utilisateur.
     *
     * @param userId l'ID de l'utilisateur
     * @return une liste d'emprunts
     */
    public List<Borrowing> getBorrowingsByUserId(Long userId) {
        return borrowingRepository.findByUserId(userId);
    }

    /**
     * Récupère tous les emprunts.
     *
     * @return une liste d'emprunts
     */
    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }
}
