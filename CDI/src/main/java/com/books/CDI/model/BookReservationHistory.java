package com.books.CDI.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_reservation_history")
public class BookReservationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // "BORROW" ou "RESERVE"
    private LocalDateTime actionDate;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    // Constructors
    public BookReservationHistory() {}

    public BookReservationHistory(String action, LocalDateTime actionDate, User user, Book book) {
        this.action = action;
        this.actionDate = actionDate;
        this.user = user;
        this.book = book;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }
    // toString, equals, hashCode (optionnel)
}
