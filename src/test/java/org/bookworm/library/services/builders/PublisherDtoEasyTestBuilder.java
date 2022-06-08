package org.bookworm.library.services.builders;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import org.bookworm.library.dto.PublisherDto;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.natpryce.makeiteasy.Property.newProperty;

public class PublisherDtoEasyTestBuilder {
    public static final int MIN_ID = 1;
    public static final int MAX_ID = 20000;

    public static final Property<PublisherDto, Integer> PublisherId = newProperty();
    public static final Property<PublisherDto, String> PublisherName = newProperty();

    private static PublisherDto publisherDto;

    public static Instantiator<PublisherDto> PublisherDtoStandardItem = (lookup) -> {
        if (Objects.isNull(publisherDto)) {
            var publisherId = 101;
            var publisherName = "Amber Publishing";

            var id = lookup.valueOf(PublisherId, ThreadLocalRandom.current().nextInt(MIN_ID, MAX_ID));
            var name = lookup.valueOf(PublisherName, publisherName);

            publisherDto = new PublisherDto(id, name);
        }
        return publisherDto;
    };
}
