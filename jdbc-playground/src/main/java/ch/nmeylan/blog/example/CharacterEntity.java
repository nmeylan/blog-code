package ch.nmeylan.blog.example;


import jakarta.persistence.*;

@Entity
@Table(name = "characters")
public class CharacterEntity {
    @Id
    protected String id;

    @OneToOne
    protected InventoryEntity inventory;

    @OneToOne
    @JoinColumn(nullable = false, name = "account_id")
    protected AccountEntity account;

}
