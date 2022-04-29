package org.bookworm.library.services.builders;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import org.bookworm.library.dto.AuthorDto;

import java.util.Objects;
import java.util.UUID;

import static com.natpryce.makeiteasy.Property.newProperty;

public class AuthorDtoEasyTestBuilder {
    public static final Property<AuthorDto, UUID> AuthorId = newProperty();
    public static final Property<AuthorDto, String> LastName = newProperty();
    public static final Property<AuthorDto, String> FirstName = newProperty();
    public static final Property<AuthorDto, String> DisplayName = newProperty();
    public static final Property<AuthorDto, String> Comment = newProperty();

    private static AuthorDto authorDto;

    public static Instantiator<AuthorDto> AuthorDtoStandardItem = (lookup) -> {
        if (Objects.isNull(authorDto)) {
            var authorFirstName = "Jan";
            var authorLastName = "Szydło";

            var id = lookup.valueOf(AuthorId, UUID.randomUUID());
            var lastName = lookup.valueOf(LastName, authorLastName);
            var firstName = lookup.valueOf(FirstName, authorFirstName);
            var displayName = lookup.valueOf(DisplayName, authorFirstName + " " + authorLastName);
            var comment = lookup.valueOf(Comment, "comment");

            authorDto = new AuthorDto(id, lastName, firstName, displayName, comment);
        }
        return authorDto;
    };


}
