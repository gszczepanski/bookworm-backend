package org.bookworm.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@Data
public class AuthorDto implements Serializable {

    private UUID id;
    private String lastName;
    private String firstName;
    private String displayName;
    private String comment;
}
