package com.system.tintin.service;

import com.system.tintin.entity.Books;
import com.system.tintin.pojo.BooksPojo;

import java.io.IOException;
import java.util.List;

public interface BooksService {

    String save(BooksPojo booksPojo) throws IOException;

    List<Books> fetchAll();

    Books fetchById(Integer id);

    void deleteById(Integer id);
}
