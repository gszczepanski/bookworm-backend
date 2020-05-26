package org.bookworm.library.mappers;

import org.bookworm.library.dto.PublisherDto;
import org.bookworm.library.entities.Publisher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    Publisher toPublisher(PublisherDto publisherDto);

    PublisherDto toDto(Publisher publisher);
}
