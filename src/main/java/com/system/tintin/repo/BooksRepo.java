package com.system.tintin.repo;

import com.system.tintin.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepo extends JpaRepository<Books, Integer> {
    @Query(value = "select * from books where name=?1", nativeQuery = true)
    Optional<Books> findBybook_id(String name);
}
