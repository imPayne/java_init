package com.books.CDI.repository;

import com.books.CDI.model.BookReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReservationHistoryRepository extends JpaRepository<BookReservationHistory, Long> {
    List<BookReservationHistory> findByUserId(Long userId);
}
