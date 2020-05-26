package org.bookworm.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bookworm.library.entities.Book;

import java.util.Set;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@Data
public class AuthorDto {

    private Integer id;
    private String lastName;
    private String firstName;
    private String displayName;
    private String comment;
    private Set<Book> books;
}
