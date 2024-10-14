package com.books.CDI.repository;

import com.books.CDI.model.Reserving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservingRepository extends JpaRepository<Reserving, Long> {
    List<Reserving> findByUserId(Long userId);
}
