package ch.nmeylan.blog.example.bookstore;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;

    protected String isbn;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;

    @ManyToOne
    @JoinColumn(name = "secondary_author_id", nullable = true)
    private AuthorEntity secondaryAuthor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private BookDetailsEntity details;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories = new HashSet<>();

    public BookEntity() {}

    // Getters and setters
}