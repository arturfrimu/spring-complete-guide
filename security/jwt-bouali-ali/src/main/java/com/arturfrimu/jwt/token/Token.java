package com.arturfrimu.jwt.token;

import com.arturfrimu.jwt.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = PRIVATE)
public class Token {
    @Id
    @GeneratedValue
    Integer id;
    @Column(unique = true)
    String token;
    @Enumerated(EnumType.STRING)
    TokenType tokenType = TokenType.BEARER;
    boolean revoked;
    boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
