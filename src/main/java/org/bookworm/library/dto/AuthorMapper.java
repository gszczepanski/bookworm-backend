package org.bookworm.library.dto;

import org.bookworm.library.entities.Author;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface AuthorMapper {

    @Mapping(target = "books", ignore = true)
    Author toAuthor(AuthorDto authorDto);

    AuthorDto toDto(Author author);
}
