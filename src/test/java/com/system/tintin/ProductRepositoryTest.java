package com.system.tintin;

import com.system.tintin.entity.Books;
import com.system.tintin.entity.User;
import com.system.tintin.repo.BooksRepo;
import com.system.tintin.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTest {
    @Autowired
    private BooksRepo booksRepo;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveBooks() {

        Books books = Books.builder()
                .name("TestName")
                .quantity("1")
                .price("1000")
                .build();
//
        booksRepo.save(books);
        Assertions.assertThat(books.getId()).isGreaterThan(0);

    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getBookTest() {
        Books books = booksRepo.findById(1).get();
        Assertions.assertThat(books.getId()).isEqualTo(1);
    }

    //
    @Test
    @Order(3)
    public void fetchAll() {
        List<Books> books = booksRepo.findAll();
        Assertions.assertThat(books.size()).isGreaterThan(0);
    }
    //
    @Test
    @Order(4)
    @Rollback(value = false)
    public void Update() {
        Books books = booksRepo.findById(1).get();
        books.setName("TestName");
        Books books1 = booksRepo.save(books);
        Assertions.assertThat(books1.getName()).isEqualTo("TestName");
    }
    //
    @Test
    @Order(5)
    @Rollback(value = false)
    public void Delete(){
        Books books =booksRepo.findById(1).get();
        booksRepo.delete(books);
        Books books1=null;
        Optional<Books> bookOptional=booksRepo.findBybook_id(books1.getName());
        if(bookOptional.isPresent()){
            books1 = bookOptional.get();
        }
        Books books2=booksRepo.save(books);
        Assertions.assertThat(books2).isNull();
    }
}
