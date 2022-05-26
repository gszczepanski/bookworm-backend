package org.bookworm.library.services.builders;

import com.natpryce.makeiteasy.Property;
import org.bookworm.library.dto.PublisherDto;

import static com.natpryce.makeiteasy.Property.newProperty;

public class PersonDtoEasyTestBuilder {
    public static final Property<PublisherDto, Integer> PersonID = newProperty();
    public static final Property<PublisherDto, String> LastName = newProperty();
    public static final Property<PublisherDto, String> MiddleName = newProperty();
    public static final Property<PublisherDto, String> FirstName = newProperty();
    public static final Property<PublisherDto, String> IdCardNumber = newProperty();
    public static final Property<PublisherDto, String> IdCardType = newProperty();
    public static final Property<PublisherDto, String> Type = newProperty();
}
