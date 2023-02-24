package com.system.tintin.service.impl;

import com.system.tintin.entity.BookCart;
import com.system.tintin.pojo.BookCartPojo;
import com.system.tintin.repo.BookCartRepo;
import com.system.tintin.repo.BooksRepo;
import com.system.tintin.repo.UserRepo;
import com.system.tintin.service.BookCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookCartServiceImpl implements BookCartService {
    private final UserRepo userRepo;
    private final BookCartRepo bookCartRepo;
    private final BooksRepo booksRepo;

    @Override
    public List<BookCart> fetchAll() {
        return this.bookCartRepo.findAll();
    }

    @Override
    public BookCartPojo save(BookCartPojo bookCartPojo) {
        BookCart bookCart = new BookCart();
        if(bookCartPojo.getId()!=null){
            bookCart.setId(bookCartPojo.getId());
        }
        bookCart.setBooks(booksRepo.findById(bookCartPojo.getBooks_id()).orElseThrow());
        bookCart.setUser(userRepo.findById(bookCartPojo.getUser_id()).orElseThrow());
        bookCartRepo.save(bookCart);
        return new BookCartPojo();
    }

    @Override
    public BookCart fetchOne(Integer id) {
        return bookCartRepo.findById(id).orElseThrow();
    }

    @Override
    public void deleteFromCart(Integer id) {
        bookCartRepo.deleteById(id);
    }

    @Override
    public String updateQuantity(BookCart bookCart) {
        bookCartRepo.save(bookCart);
        return "Updated";
    }

    @Override
    public List<BookCart> fetchAll(Integer id) {
        return findAllInList(bookCartRepo.findAll());
    }
    public List<BookCart> findAllInList(List<BookCart> list){
        Stream<BookCart> allCart=list.stream().map(productCart ->
                BookCart.builder()
                        .id(productCart.getId())
                        .books(productCart.getBooks())
                        .user(productCart.getUser())
                        .build()
        );

        list = allCart.toList();
        return list;
    }
}
