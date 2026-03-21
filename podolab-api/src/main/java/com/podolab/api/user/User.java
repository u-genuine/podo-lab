package com.podolab.api.user;

import com.podolab.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User create(String name, String email) {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}