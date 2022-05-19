package org.bookworm.library.services.builders;

import com.natpryce.makeiteasy.Property;
import org.bookworm.library.dto.PublisherDto;

import java.util.UUID;

import static com.natpryce.makeiteasy.Property.newProperty;

public class PublisherDtoEasyTestBuilder {
    public static final Property<PublisherDto, UUID> PublisherId = newProperty();
    public static final Property<PublisherDto, String> PublisherName = newProperty();

    private static PublisherDto publisherDto;
}
