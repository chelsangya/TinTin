package com.system.tintin.controller;

import com.system.tintin.entity.BookCart;
import com.system.tintin.entity.Books;
import com.system.tintin.entity.Queries;
import com.system.tintin.entity.User;
import com.system.tintin.pojo.BooksPojo;
import com.system.tintin.service.BookCartService;
import com.system.tintin.service.BooksService;
import com.system.tintin.service.QueryService;
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
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final BooksService booksService;
    private final BookCartService bookCartService;
    private final QueryService queryService;
    @GetMapping("/order-list")
    public String getOrderListPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        assert principal != null;
        Integer id = userService.findByEmail(principal.getName()).getId();
        List<BookCart> list = bookCartService.fetchAll(id);
        model.addAttribute("cartItems", list);
        return "order_list";
    }
    @GetMapping("/user-list")
    public String getUserListPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        List<User> users = userService.fetchAll();
        model.addAttribute("userlist", users.stream().map(user ->
                User.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .number(user.getNumber())
                        .address(user.getAddress())
                        .build()
        ));
        return "userlist";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, Principal principal) {
        userService.deleteById(id);
        return "redirect:/admin/user-list";
    }
    @GetMapping("/add-books")
    public String getAddBookPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        model.addAttribute("books", new BooksPojo());
        return "add_books";
    }
    @GetMapping("/books-list")
    public String getProductList(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        List<Books> book = booksService.fetchAll();
        model.addAttribute("book", book);
        return "bookslist";
    }
    @GetMapping("/editBooks/{id}")
    public String editProducts(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        Books books = booksService.fetchById(id);
        model.addAttribute("books", new BooksPojo(books));
        return "add_books";
    }
    @GetMapping("/deleteBooks/{id}")
    public String deleteProducts(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        booksService.deleteById(id);
        return "redirect:/admin/books-list";
    }
    @PostMapping("/save/books")
    public String saveBooks(@Valid BooksPojo booksPojo) throws IOException {
        booksService.save(booksPojo);
        return "redirect:/dashboard";
    }
    @GetMapping("/queries")
    public String getQueryPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        List<Queries> queries = queryService.fetchAll();
        model.addAttribute("queries", queries);
        return "query_section";
    }

    @GetMapping("/settings")
    public String getAdminSettingsPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        return "admin_settings";
    }
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
}
