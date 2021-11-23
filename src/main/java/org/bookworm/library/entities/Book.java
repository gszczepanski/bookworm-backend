package org.bookworm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/05/13
 */
@Entity
@ToString
@Getter
@Setter
public class Book extends EntityWithUUID {

    @NotNull
    private Integer registryNumber;

    @NotNull
    @Size(max=255)
    private String title;

    @Size(max=100)
    private String placeOfOrigin;

    @NotNull
    private Integer year;

    @Size(max=20)
    private String volume;

    @NotNull
    @Column(columnDefinition="boolean default true")
    private Boolean jointPublication = false;

    @Size(max=100)
    private String seriesName;

    @Size(max=255)
    private String comment;

    @NotNull
    @Column(updatable = false)
    private LocalDate acquireDate;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private BookAcquiringMethod acquiringMethod;

    @NotNull
    private UUID acquiringEmployeeId;

    @Size(max=20)
    private String invoiceSymbol;

    private BigDecimal price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id", referencedColumnName = "id") })
    @JsonIgnore
    private List<Author> authors;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private BookStatus status;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private Language language;

    public Book() {
        List authors = new ArrayList<>();
    }

    /**
     * Optionally force client classes through your add/remove methods if mutual
     * relationship should be maintained.
     */
    public List<Author> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }
}
