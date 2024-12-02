package ch.nmeylan.blog.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    protected String id;

    private String email;
    private String password;

    private Instant lastLoginAt;

}
