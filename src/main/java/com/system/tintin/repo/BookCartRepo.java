package com.system.tintin.repo;

import com.system.tintin.entity.BookCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCartRepo extends JpaRepository<BookCart, Integer> {
    @Query(value = "SELECT * FROM BOOKSCARTS WHERE user_id = ?1", nativeQuery = true)
    Optional<List<BookCart>> fetchAll(Integer userId);
}
