package org.bookworm.library.services.builders;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import org.bookworm.library.dto.BookForModificationDto;
import org.bookworm.library.entities.BookAcquiringMethod;
import org.bookworm.library.entities.BookStatus;
import org.bookworm.library.entities.Language;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static com.natpryce.makeiteasy.Property.newProperty;

//TODO
public final class BookDtoEasyTestBuilder {
    private final Integer PUBLISHER_ID = 10;
    private final String BOOK_UUID_1 = "c0a80015-7d8c-1d2f-817d-8c2e93df2200";
    private final String BOOK_UUID_2 = "c0a80015-7d8c-1d2f-817d-8c2e93df2210";

    public static final Property<BookForModificationDto, UUID> BookId = newProperty();

    private static BookForModificationDto bookDto;

    public static Instantiator<BookForModificationDto> BookDtoStandardItem = (lookup) -> {
        if (Objects.isNull(bookDto)) {
            var id = lookup.valueOf(BookId, UUID.randomUUID());
            var registryNumber = 1;
            var title = "Red Mars";
            var placeOfOrigin = "Warsaw";
            var year = 2002;
            var volume = "1";
            var acquireDate = "2000-01-02";
            var acquiringMethod = BookAcquiringMethod.PURCHASED;
            var acquiringEmployeeUUId = UUID.fromString("28319c80-449d-11ec-81d3-0242ac130003");
            var status = BookStatus.AVAILABLE;
            var publisherId = 10;
            var language = Language.ENGLISH;
            var jointPublication = false;

            bookDto = BookForModificationDto.builder()
                    .id(id)
                    .registryNumber(registryNumber)
                    .title(title)
                    .placeOfOrigin(placeOfOrigin)
                    .year(year)
                    .volume(volume)
                    .acquireDate(LocalDate.parse(acquireDate))
                    .acquiringMethod(acquiringMethod)
                    .acquiringEmployeeId(acquiringEmployeeUUId)
                    .status(status)
                    .publisherId(publisherId)
                    .language(language)
                    .jointPublication(jointPublication)
                    .build();
        }
        return bookDto;
    };
}
