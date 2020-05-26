package org.bookworm.library.mappers;

import org.bookworm.library.dto.AuthorDto;
import org.bookworm.library.entities.Author;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface AuthorMapper {

    Author toAuthor(AuthorDto authorDto);

    AuthorDto toDto(Author author);
}
