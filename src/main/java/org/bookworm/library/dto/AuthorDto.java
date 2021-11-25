package org.bookworm.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@Data
public class AuthorDto {

    private UUID id;
    private String lastName;
    private String firstName;
    private String displayName;
    private String comment;
    private Set<BookDto> books;
}
