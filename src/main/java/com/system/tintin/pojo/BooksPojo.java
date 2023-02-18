package com.system.tintin.pojo;

import com.system.tintin.entity.Books;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksPojo {
    private Integer id;
    private MultipartFile photo;
    @NotEmpty(message = "Book Name can't be empty")
    private String name;
    @NotEmpty(message = "Book Quantity can't be empty")
    private String quantity;
    @NotEmpty(message = "Book Price can't be empty")
    private String price;

    public BooksPojo(Books books) {
        this.id = books.getId();
        this.name = books.getName();
        this.quantity = books.getQuantity();
        this.price = books.getPrice();
    }
}
