package org.bookworm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Grzegorz on 2019/05/22
 */
@Entity
@ToString
@Getter
@Setter
public class Author extends EntityWithUUID {

    @NotNull
    @Size(max=50)
    private String lastName;

    @NotNull
    @Size(max=30)
    private String firstName;

    @NotNull
    @Size(max=150)
    private String displayName;

    @Size(max=1000)
    private String comment;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonIgnore
    private List<Book> books;

    public Author() {
        List books = new ArrayList<>();
    }

    /**
     * Optionally force client classes through your add/remove methods if mutual
     * relationship should be maintained.
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Ensures mutual relationship set correctly.
     */
    public void addBook(Book book) {
        if (Objects.nonNull(book)) {
            books.add(book);
            book.getAuthors().add(this);
        }
    }

}
