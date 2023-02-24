package com.system.tintin.service;

import com.system.tintin.entity.BookCart;
import com.system.tintin.pojo.BookCartPojo;

import java.util.List;

public interface BookCartService {
    List<BookCart> fetchAll();

    BookCartPojo save(BookCartPojo bookCartPojo);

    BookCart fetchOne(Integer id);

    void deleteFromCart(Integer id);

    String updateQuantity(BookCart bookCart);

    List<BookCart> fetchAll(Integer id);
}
