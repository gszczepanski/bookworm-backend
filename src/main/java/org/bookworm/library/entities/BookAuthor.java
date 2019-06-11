package org.bookworm.library.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by Grzegorz on 2019/05/22
 */
@Entity
@ToString
@Getter
@Setter
public class BookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max=50)
    private String lastName;

    @NotNull
    @Size(max=30)
    private String firstName;

    @NotNull
    @Size(max=150)
    private String displayName;

    @NotNull
    @Size(max=1000)
    private String comment;

    @ManyToMany(mappedBy="bookAuthors", fetch = FetchType.EAGER)
    private Set<Book> books;
}
