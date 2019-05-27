package org.bookworm.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by Grzegorz on 2019/05/13
 */
@Entity
@ToString
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer registryNumber;

    @NotNull
    @Size(max=255)
    private String title;

    @NotNull
    @Size(max=255)
    private String publisherName;

    @Size(max=100)
    private String placeOfOrigin;

    @NotNull
    private Integer year;

    @NotNull
    @Size(max=20)
    private String volume;

    @NotNull
    @Column(columnDefinition="boolean default true")
    private Boolean jointPublication = false;

    @Size(max=100)
    private String seriesName;

    private String comment;

    @NotNull
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date acquireDate;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private BookAcquiringMethod acquiringMethod;

    @NotNull
    private Integer acquiringEmployeerId;

    @Size(max=20)
    private String invoiceSymbol;

    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = { @JoinColumn(name = "author_id") })
    private Set<Author> authors;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private BookStatus status;
}
