package ch.nmeylan.blog.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "inventories")
public class InventoryEntity {
    @Id
    protected String id;

    @OneToOne
    @JoinColumn(nullable = false, name = "char_id")
    private  CharacterEntity characterEntity;

    @OneToMany
    @JoinColumn(nullable = false, name = "id")
    protected List<ItemEntity> items;

    protected Instant lastUpdatedAt;

    @Transient
    private long internalId;

}
