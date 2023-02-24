package com.system.tintin.controller;

import com.system.tintin.entity.Books;
import com.system.tintin.pojo.BookCartPojo;
import com.system.tintin.service.BookCartService;
import com.system.tintin.service.BooksService;
import com.system.tintin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor //creates the constructor with all required arguments
@RequestMapping("/dashboard")
public class DashboardController {
    private final BooksService booksService;
    private final BookCartService bookCartService;
    private final UserService userService;
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
    @GetMapping("")
    public String getDashboard(Model model, Principal principal, Authentication authentication){
        if (authentication!=null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("Admin")) {
                    return "redirect:/admin/order-list";
                }
            }
        }
        List<Books> book = booksService.fetchAll();
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
//        model.addAttribute("books", book.stream().map(books ->
//                        Books.builder()
//                                .id(books.getId())
//                                .imageBase64(getImageBase64(books.getPhoto()))
//                                .name(books.getName())
//                                .quantity(books.getQuantity())
//                                .price(books.getPrice())
//                                .build()
//                )
//        );
        model.addAttribute("book", book);
        model.addAttribute("savecarts", new BookCartPojo());
        return "dashboard";
    }

    @PostMapping("/save")
    public String savecart(@Valid BookCartPojo bookCartPojo) {
        bookCartService.save(bookCartPojo);
        return "redirect:/login";
    }
    @GetMapping("/aboutus")
    public String getAboutUsPage() {
        return "aboutus";
    }
}
