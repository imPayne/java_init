package com.books.CDI.service;

import com.books.CDI.exception.ResourceNotFoundException;
import com.books.CDI.model.Book;
import com.books.CDI.model.Reserving;
import com.books.CDI.model.User;
import com.books.CDI.repository.ReservingRepository;
import com.books.CDI.repository.BookRepository;
import com.books.CDI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservingService {

    @Autowired
    private ReservingRepository reservingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Réserve un livre pour un utilisateur avec une priorité.
     *
     * @param userId              l'ID de l'utilisateur
     * @param bookId              l'ID du livre
     * @param reservationPriority la priorité de réservation
     * @return la réservation créée
     * @throws ResourceNotFoundException si l'utilisateur ou le livre n'est pas trouvé
     * @throws IllegalStateException     si le livre est disponible et peut être emprunté directement
     */
    @Transactional
    public Reserving reserveBook(Long userId, Long bookId, int reservationPriority) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        if (!book.isBorrowed()) {
            throw new IllegalStateException("Book is available. You can borrow it directly.");
        }

        // Créer une nouvelle réservation
        Reserving reserving = new Reserving();
        reserving.setUser(user);
        reserving.setBook(book);
        reserving.setReservationPriority(reservationPriority);
        reserving.setReservedAt(LocalDateTime.now());

        return reservingRepository.save(reserving);
    }

    /**
     * Annule une réservation.
     *
     * @param reservingId l'ID de la réservation
     * @return la réservation supprimée
     * @throws ResourceNotFoundException si la réservation n'est pas trouvée
     */
    @Transactional
    public void cancelReserving(Long reservingId) {
        Reserving reserving = reservingRepository.findById(reservingId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserving", "id", reservingId));

        reservingRepository.delete(reserving);
    }

    /**
     * Récupère toutes les réservations d'un utilisateur.
     *
     * @param userId l'ID de l'utilisateur
     * @return une liste de réservations
     */
    public List<Reserving> getReservingsByUserId(Long userId) {
        return reservingRepository.findByUserId(userId);
    }

    /**
     * Récupère toutes les réservations.
     *
     * @return une liste de réservations
     */
    public List<Reserving> getAllReservings() {
        return reservingRepository.findAll();
    }
}
