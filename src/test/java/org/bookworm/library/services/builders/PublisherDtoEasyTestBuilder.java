package org.bookworm.library.services.builders;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import org.bookworm.library.dto.PublisherDto;

import java.util.Objects;

import static com.natpryce.makeiteasy.Property.newProperty;

public class PublisherDtoEasyTestBuilder {
    public static final Property<PublisherDto, Integer> PublisherId = newProperty();
    public static final Property<PublisherDto, String> PublisherName = newProperty();

    private static PublisherDto publisherDto;

    public static Instantiator<PublisherDto> PublisherDtoStandardItem = (lookup) -> {
        if (Objects.isNull(publisherDto)) {
            var publisherId = 101;
            var publisherName = "Amber Publishing";

            var id = lookup.valueOf(PublisherId, publisherId);
            var name = lookup.valueOf(PublisherName, publisherName);

            publisherDto = new PublisherDto(id, name);
        }
        return publisherDto;
    };
}
