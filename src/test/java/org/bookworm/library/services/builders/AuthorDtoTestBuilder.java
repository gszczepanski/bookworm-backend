package org.bookworm.library.services.builders;

import org.bookworm.library.dto.AuthorDto;

import java.util.UUID;

@Deprecated
public final class AuthorDtoTestBuilder {

    private static final UUID AUTHOR_UUID = UUID.randomUUID();
    private static AuthorDto authorDto;

    private static void initializeAutorDto() {
        String AUTHOR_FIRST_NAME = "Jan";
        String AUTHOR_LAST_NAME = "Kowalski";

        authorDto = new AuthorDto();
        authorDto.setId(AUTHOR_UUID);
        authorDto.setDisplayName(AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME);
        authorDto.setFirstName(AUTHOR_FIRST_NAME);
        authorDto.setLastName(AUTHOR_LAST_NAME);
    }

    public static AuthorDto standardItem() {
        if (authorDto == null) {
            initializeAutorDto();
        }
        return authorDto;
    }

    public static UUID standardKey() {
        return AUTHOR_UUID;
    }
}
