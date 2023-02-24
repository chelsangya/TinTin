package com.system.tintin.pojo;

import com.system.tintin.entity.BookCart;
import com.system.tintin.entity.Books;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCartPojo {
    private Integer id;

    private Integer user_id;
    private Integer books_id;

    public BookCartPojo(BookCart bookCart) {
        this.id = bookCart.getId();
        this.user_id = bookCart.getUser().getId();
        this.books_id = bookCart.getBooks().getId();
    }
}
