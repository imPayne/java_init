package com.books.CDI.service;

import com.books.CDI.exception.ResourceNotFoundException;
import com.books.CDI.model.BookReservationHistory;
import com.books.CDI.repository.BookRepository;
import com.books.CDI.repository.BookReservationHistoryRepository;
import com.books.CDI.repository.UserRepository;
import com.books.CDI.model.User;
import com.books.CDI.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookReservationHistoryService {

    @Autowired
    private BookReservationHistoryRepository bookReservationHistoryRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    /**
     * Ajoute une entrée dans l'historique des réservations/emprunts.
     *
     * @param userId l'ID de l'utilisateur
     * @param bookId l'ID du livre
     * @param action "BORROW" ou "RESERVE"
     * @return l'entrée d'historique créée
     * @throws ResourceNotFoundException si l'utilisateur ou le livre n'est pas trouvé
     */
    public BookReservationHistory addHistory(Long userId, Long bookId, String action) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        BookReservationHistory history = new BookReservationHistory();
        history.setUser(user);
        history.setBook(book);
        history.setAction(action);
        history.setActionDate(LocalDateTime.now());

        return bookReservationHistoryRepository.save(history);
    }

    /**
     * Récupère l'historique des réservations/emprunts d'un utilisateur.
     *
     * @param userId l'ID de l'utilisateur
     * @return une liste d'entrées d'historique
     */
    public List<BookReservationHistory> getHistoryByUserId(Long userId) {
        return bookReservationHistoryRepository.findByUserId(userId);
    }

    /**
     * Récupère tout l'historique des réservations/emprunts.
     *
     * @return une liste d'entrées d'historique
     */
    public List<BookReservationHistory> getAllHistory() {
        return bookReservationHistoryRepository.findAll();
    }
}