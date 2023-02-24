package com.system.tintin.service.impl;

import com.system.tintin.entity.Books;
import com.system.tintin.pojo.BooksPojo;
import com.system.tintin.repo.BookCartRepo;
import com.system.tintin.repo.BooksRepo;
import com.system.tintin.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    public final BooksRepo booksRepo;
    public final BookCartRepo bookCartRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/tintinbooks";
    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/tintinbooks/";
        File file = new File(filePath + fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }


    @Override
    public String save(BooksPojo booksPojo) throws IOException {
        Books books;
        if (booksPojo.getId() != null) {
            books = booksRepo.findById(booksPojo.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        } else {
            books = new Books();
        }
        if(booksPojo.getId()!=null){
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
        return findAllInList(booksRepo.findAll());
    }

    private List<Books> findAllInList(List<Books> list) {
        Stream<Books> allCart=list.stream().map(books ->
                Books.builder()
                        .id(books.getId())
                        .imageBase64(getImageBase64(books.getPhoto()))
                        .name(books.getName())
                        .quantity(books.getQuantity())
                        .price(books.getPrice())
                        .build()
        );

        list = allCart.toList();
        return list;
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
