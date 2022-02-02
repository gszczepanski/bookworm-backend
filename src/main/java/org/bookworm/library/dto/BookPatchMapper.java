package org.bookworm.library.dto;

import org.bookworm.library.entities.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring",// uses = {AuthorMapper.class, PublisherMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookPatchMapper {
    
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    Book updateBookFromDto(BookForModificationDto bookForModificationDto, @MappingTarget Book book);
}
