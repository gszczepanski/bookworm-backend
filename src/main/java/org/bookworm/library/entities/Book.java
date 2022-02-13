package org.bookworm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bookworm.library.entities.groups.OnCreate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @NotNull(groups = OnCreate.class)
    @Column(nullable = false)
    private Integer registryNumber;

    @NotBlank(groups = OnCreate.class)
    @Column(nullable = false)
    @Size(max = 255)
    private String title;

    @Size(max = 100)
    private String placeOfOrigin;

    @NotNull(groups = OnCreate.class)
    @Column(nullable = false)
    private Integer year;

    @Size(max = 20)
    @Column(nullable = false)
    private String volume;

    @NotNull(groups = OnCreate.class)
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean jointPublication;

    @Size(max = 100)
    private String seriesName;

    @Size(max = 255)
    private String comment;

    @NotNull(groups = OnCreate.class)
    @Column(nullable = false, updatable = false)
    private LocalDate acquireDate;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(groups = OnCreate.class)
    @Column(nullable = false)
    private BookAcquiringMethod acquiringMethod;

    @NotNull(groups = OnCreate.class)
    @Column(nullable = false)
    private UUID acquiringEmployeeId;

    @Size(max = 20)
    private String invoiceSymbol;

    private BigDecimal price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")})
    @JsonIgnore
    @ToString.Exclude
    private List<Author> authors;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(groups = OnCreate.class)
    @Column(nullable = false)
    private BookStatus status;

    @ManyToOne(targetEntity = Publisher.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private Publisher publisher;

    @Column(nullable = false, name = "publisher_id")
    private Long publisherId;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(groups = OnCreate.class)
    @Column(nullable = false)
    private Language language;

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
