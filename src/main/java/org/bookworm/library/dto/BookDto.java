package org.bookworm.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bookworm.library.entities.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@Data
public class BookDto {

    private UUID id;
    private Integer registryNumber;
    private String title;
    private String placeOfOrigin;
    private Integer year;
    private String volume;
    private Boolean jointPublication;
    private String seriesName;
    private String comment;
    private LocalDate acquireDate;
    private BookAcquiringMethod acquiringMethod;
    private Integer acquiringEmployeeId;
    private String invoiceSymbol;
    private BigDecimal price;
    private Set<Author> authors;
    private BookStatus status;
    private Publisher publisher;
    private Language language;
}
