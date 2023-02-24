package com.system.tintin.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booksCarts")
public class BookCart {
    @Id
    @SequenceGenerator(name = "booksCarts_seq_gen", sequenceName = "booksCarts_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "booksCarts_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "books_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_productId"))
    private Books books;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_userId"))
    private User user;
}
