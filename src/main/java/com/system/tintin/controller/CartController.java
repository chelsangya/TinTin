package com.system.tintin.controller;
import com.system.tintin.entity.BookCart;
import com.system.tintin.entity.Books;
import com.system.tintin.pojo.BookCartPojo;
import com.system.tintin.pojo.BooksPojo;
import com.system.tintin.service.BookCartService;
import com.system.tintin.service.BooksService;
import com.system.tintin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final BooksService booksService;

    private final UserService userService;
    private final BookCartService bookCartService;

    @GetMapping()
    public String displayCart(Principal principal, Model model, BookCartPojo bookCartPojo){
        Integer id = userService.findByEmail(principal.getName()).getId();
        List<BookCart> list = bookCartService.fetchAll(id);
        model.addAttribute("cart", bookCartPojo);
        model.addAttribute("cartItems", list);
        return "cart";
    }

    @PostMapping("/updateQuantity/{id}")
    public String updateQuantity(@Valid BookCartPojo bookCartPojo){
        BookCart bookCart = bookCartService.fetchOne(bookCartPojo.getId());
        bookCartService.updateQuantity(bookCart);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String deleteCartItem(@PathVariable("id") Integer id){
        bookCartService.deleteFromCart(id);
        return "redirect:/cart";
    }
}