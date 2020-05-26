package org.bookworm.library.mappers;

import org.bookworm.library.dto.BookDto;
import org.bookworm.library.entities.Book;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

/**
 * Created by Grzegorz on 2020/05/25
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class, PublisherMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface BookMapper {

    Book toBook(BookDto bookDto);

    BookDto toDto(Book book);
}
