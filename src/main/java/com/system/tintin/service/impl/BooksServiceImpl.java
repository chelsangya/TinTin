package com.system.tintin.service.impl;

import com.system.tintin.entity.Books;
import com.system.tintin.pojo.BooksPojo;
import com.system.tintin.repo.BooksRepo;
import com.system.tintin.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    public final BooksRepo booksRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/tintinbooks";


    @Override
    public String save(BooksPojo booksPojo) throws IOException {
        Books books=new Books();
        if(books.getId()!=null){
            books.setId(booksPojo.getId());
        }
        books.setName(booksPojo.getName());
        books.setQuantity(booksPojo.getQuantity());
        books.setPrice(booksPojo.getPrice());
        if(booksPojo.getPhoto()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, booksPojo.getPhoto().getOriginalFilename());
            fileNames.append(booksPojo.getPhoto().getOriginalFilename());
            Files.write(fileNameAndPath, booksPojo.getPhoto().getBytes());

            books.setPhoto(booksPojo.getPhoto().getOriginalFilename());
        }

        booksRepo.save(books);
        return "created";
    }

    @Override
    public List<Books> fetchAll() {
        return this.booksRepo.findAll();
    }

    @Override
    public Books fetchById(Integer id) {
        return booksRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
    }

    @Override
    public void deleteById(Integer id) {
        booksRepo.deleteById(id);
    }
}
