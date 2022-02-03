package org.bookworm.library.dto;

import org.bookworm.library.entities.Publisher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    Publisher toPublisher(PublisherDto publisherDto);

    PublisherDto toDto(Publisher publisher);
}
